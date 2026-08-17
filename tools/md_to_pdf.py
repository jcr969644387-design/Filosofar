#!/usr/bin/env python3
"""
Convierte un subconjunto de Markdown (encabezados #/##/###, listas -, tablas
| | |, negrita **texto**, bloques de código ```, citas > ) a un PDF con
ReportLab Platypus. Suficiente para la documentación de Filosofar sin
depender de herramientas externas de conversión.
"""
import re
import sys
from reportlab.lib.pagesizes import LETTER
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, ListFlowable,
    ListItem, Preformatted, PageBreak
)

def inline_md(text):
    text = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    text = re.sub(r"\*\*(.+?)\*\*", r"<b>\1</b>", text)
    text = re.sub(r"`(.+?)`", r"<font face='Courier'>\1</font>", text)
    return text

def build_styles():
    styles = getSampleStyleSheet()
    styles.add(ParagraphStyle(name="H1Custom", fontSize=20, leading=24, spaceAfter=14, spaceBefore=6, textColor=colors.HexColor("#0B2447"), fontName="Helvetica-Bold"))
    styles.add(ParagraphStyle(name="H2Custom", fontSize=15, leading=19, spaceAfter=10, spaceBefore=16, textColor=colors.HexColor("#19376D"), fontName="Helvetica-Bold"))
    styles.add(ParagraphStyle(name="H3Custom", fontSize=12.5, leading=16, spaceAfter=8, spaceBefore=10, textColor=colors.HexColor("#1AA89B"), fontName="Helvetica-Bold"))
    styles.add(ParagraphStyle(name="BodyCustom", fontSize=10.3, leading=15, spaceAfter=8, fontName="Helvetica"))
    styles.add(ParagraphStyle(name="BulletCustom", fontSize=10.3, leading=14.5, fontName="Helvetica"))
    styles.add(ParagraphStyle(name="QuoteCustom", fontSize=9.8, leading=14, leftIndent=14, textColor=colors.HexColor("#444444"), borderColor=colors.HexColor("#3FE0D0"), borderWidth=0, spaceAfter=8))
    styles.add(ParagraphStyle(name="CodeCustom", fontName="Courier", fontSize=8.3, leading=11, backColor=colors.HexColor("#F2F2F2")))
    return styles

def parse_table(lines, start):
    rows = []
    i = start
    while i < len(lines) and lines[i].strip().startswith("|"):
        line = lines[i].strip()
        if re.match(r"^\|[\s\-:|]+\|$", line):
            i += 1
            continue
        cells = [c.strip() for c in line.strip("|").split("|")]
        rows.append(cells)
        i += 1
    return rows, i

def md_to_flowables(md_text, styles):
    flow = []
    lines = md_text.split("\n")
    i = 0
    in_code = False
    code_buf = []
    list_buf = []

    def flush_list():
        nonlocal list_buf
        if list_buf:
            items = [ListItem(Paragraph(inline_md(t), styles["BulletCustom"]), leftIndent=12) for t in list_buf]
            flow.append(ListFlowable(items, bulletType="bullet", start="•", leftIndent=14))
            flow.append(Spacer(1, 6))
            list_buf = []

    while i < len(lines):
        line = lines[i]
        stripped = line.strip()

        if stripped.startswith("```"):
            if not in_code:
                in_code = True
                code_buf = []
            else:
                in_code = False
                flush_list()
                flow.append(Preformatted("\n".join(code_buf), styles["CodeCustom"]))
                flow.append(Spacer(1, 8))
            i += 1
            continue
        if in_code:
            code_buf.append(line)
            i += 1
            continue

        if not stripped:
            flush_list()
            i += 1
            continue

        if stripped.startswith("# "):
            flush_list()
            flow.append(Paragraph(inline_md(stripped[2:]), styles["H1Custom"]))
            i += 1
            continue
        if stripped.startswith("## "):
            flush_list()
            flow.append(Paragraph(inline_md(stripped[3:]), styles["H2Custom"]))
            i += 1
            continue
        if stripped.startswith("### "):
            flush_list()
            flow.append(Paragraph(inline_md(stripped[4:]), styles["H3Custom"]))
            i += 1
            continue

        if stripped.startswith("> "):
            flush_list()
            flow.append(Paragraph(inline_md(stripped[2:]), styles["QuoteCustom"]))
            i += 1
            continue

        if stripped.startswith("|"):
            flush_list()
            rows, next_i = parse_table(lines, i)
            if rows:
                data = [[Paragraph(inline_md(c), styles["BulletCustom"]) for c in row] for row in rows]
                t = Table(data, hAlign="LEFT", repeatRows=1)
                t.setStyle(TableStyle([
                    ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#0B2447")),
                    ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
                    ("GRID", (0, 0), (-1, -1), 0.5, colors.HexColor("#CCCCCC")),
                    ("VALIGN", (0, 0), (-1, -1), "TOP"),
                    ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, colors.HexColor("#F2F6FC")]),
                    ("FONTSIZE", (0, 0), (-1, -1), 8.7),
                    ("TOPPADDING", (0, 0), (-1, -1), 4),
                    ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
                ]))
                flow.append(t)
                flow.append(Spacer(1, 10))
            i = next_i
            continue

        if re.match(r"^[-*]\s+", stripped):
            list_buf.append(re.sub(r"^[-*]\s+", "", stripped))
            i += 1
            continue

        if stripped == "---":
            flush_list()
            flow.append(Spacer(1, 4))
            i += 1
            continue

        flush_list()
        flow.append(Paragraph(inline_md(stripped), styles["BodyCustom"]))
        i += 1

    flush_list()
    return flow

def convert(md_path, pdf_path, title):
    with open(md_path, encoding="utf-8") as f:
        md_text = f.read()

    # Elimina bloques mermaid (no soportado en el render de texto; se documenta aparte)
    md_text = re.sub(r"```mermaid.*?```", "[Ver diagrama entidad-relación completo en docs/BASE_DE_DATOS.md, formato Mermaid]", md_text, flags=re.S)

    styles = build_styles()
    doc = SimpleDocTemplate(
        pdf_path, pagesize=LETTER,
        topMargin=2 * cm, bottomMargin=2 * cm, leftMargin=2 * cm, rightMargin=2 * cm,
        title=title, author="EducaLab"
    )
    flow = md_to_flowables(md_text, styles)
    doc.build(flow)
    print(f"OK: {pdf_path}")

if __name__ == "__main__":
    md_path, pdf_path, title = sys.argv[1], sys.argv[2], sys.argv[3]
    convert(md_path, pdf_path, title)
