-- ============================================================
-- FILOSOFAR — DATOS DE MUESTRA
-- Este archivo NO es el seeder real de la app (que vive en
-- app/.../data/local/seed/*.kt y se ejecuta en Kotlin al primer
-- arranque). Es una muestra representativa en SQL puro, útil
-- para inspeccionar el esquema con cualquier cliente SQLite,
-- para pruebas manuales, o como referencia de formato de datos.
-- ============================================================

-- ------------------------------------------------------------
-- Islas (6 de 6 — tabla completa)
-- ------------------------------------------------------------
INSERT INTO philosophy_island (id, name, tagline, sortOrder, themeColorHex, iconKey, unlockRequiredCrystals) VALUES
('isla_verdad', 'Isla de la Verdad', '¿Cómo sabemos lo que sabemos?', 0, '#2E86AB', 'island_truth', 0),
('isla_justicia', 'Isla de la Justicia', '¿Qué hace que algo sea justo?', 1, '#8E44AD', 'island_justice', 3),
('isla_amistad', 'Isla de la Amistad', '¿Qué necesita una amistad para durar?', 2, '#E67E22', 'island_friendship', 6),
('isla_libertad', 'Isla de la Libertad', '¿Hasta dónde llega mi libertad?', 3, '#16A085', 'island_freedom', 9),
('isla_responsabilidad', 'Isla de la Responsabilidad', '¿De qué soy realmente responsable?', 4, '#C0392B', 'island_responsibility', 12),
('isla_convivencia', 'Isla de la Convivencia', '¿Cómo vivimos bien entre todos?', 5, '#27AE60', 'island_coexistence', 15);

-- ------------------------------------------------------------
-- Preguntas del día (muestra: 2 por isla; el set completo tiene 30)
-- ------------------------------------------------------------
INSERT INTO daily_question (id, islandId, text, hint, orderInIsland) VALUES
('isla_verdad_q1', 'isla_verdad', '¿Algo deja de ser verdad si nadie lo cree?', 'Piensa en algo que la gente creía hace mucho tiempo y hoy sabemos que no era así.', 0),
('isla_verdad_q2', 'isla_verdad', '¿Puedes estar seguro de algo sin haberlo visto tú mismo?', 'Piensa en cómo sabes que existen lugares que nunca has visitado.', 1),
('isla_justicia_q1', 'isla_justicia', '¿Repartir algo en partes iguales es siempre lo más justo?', 'Piensa en repartir un pastel entre alguien que ya comió y alguien que no.', 0),
('isla_amistad_q1', 'isla_amistad', '¿Un amigo tiene que estar de acuerdo contigo siempre?', 'Piensa en una vez que discutiste con un amigo y seguisteis siéndolo.', 0),
('isla_libertad_q1', 'isla_libertad', '¿Ser libre significa poder hacer todo lo que quieras?', 'Piensa en qué pasaría si todos hicieran solo lo que quieren, sin límites.', 0),
('isla_responsabilidad_q1', 'isla_responsabilidad', 'Si rompes algo sin querer, ¿eres igual de responsable que si lo haces a propósito?', 'Piensa en la diferencia entre un accidente y una decisión.', 0),
('isla_convivencia_q1', 'isla_convivencia', '¿Por qué necesitamos normas para vivir juntos?', 'Piensa en qué pasaría en un parque sin ninguna norma.', 0);

-- ------------------------------------------------------------
-- Dilema de muestra con sus 3 opciones (el set completo tiene 12 dilemas)
-- ------------------------------------------------------------
INSERT INTO dilemma (id, islandId, title, scenario, orderInIsland) VALUES
('dil_verdad_1', 'isla_verdad', 'El examen encontrado', 'Antes del examen de mates, ves por accidente una copia con las respuestas sobre la mesa del profe.', 0);

INSERT INTO dilemma_option (id, dilemmaId, label, consequence, lumiView, noxView, sortOrder) VALUES
('dil_verdad_1_opt1', 'dil_verdad_1', 'La leo rápido y la uso', 'Sacas mejor nota, pero no sabes si de verdad aprendiste.', 'Lumi piensa: usarla no te ayuda a entender los problemas, solo a pasar el examen.', 'Nox piensa: si la encontraste sin buscarla, no planeaste hacer trampa.', 0),
('dil_verdad_1_opt2', 'dil_verdad_1', 'Se la devuelvo al profe sin mirarla', 'Pierdes la ventaja, pero el examen mide lo que realmente sabes.', 'Lumi piensa: así la nota sí cuenta como algo tuyo de verdad.', 'Nox piensa: también está bien sentir la tentación; lo importante es qué haces con ella.', 1),
('dil_verdad_1_opt3', 'dil_verdad_1', 'Se lo cuento a un compañero para que decida él', 'Compartes la decisión, pero también el problema.', 'Lumi piensa: cada quien debería decidir esto por sí mismo.', 'Nox piensa: pedir opinión no es malo, a veces ayuda a pensar mejor.', 2);

-- ------------------------------------------------------------
-- Cartas de razones (tabla completa: 24)
-- ------------------------------------------------------------
INSERT INTO reason_card (id, text, valueTags, iconKey) VALUES
('rc_01', 'Porque así nadie sale perjudicado', 'justicia,cuidado', 'scale'),
('rc_02', 'Porque es lo que me gustaría que hicieran conmigo', 'empatia,justicia', 'heart'),
('rc_03', 'Porque una promesa se debe cumplir', 'confianza,responsabilidad', 'handshake'),
('rc_04', 'Porque todos merecen la misma oportunidad', 'justicia,igualdad', 'scale'),
('rc_05', 'Porque decir la verdad evita problemas mayores', 'verdad,confianza', 'compass'),
('rc_06', 'Porque cada persona tiene derecho a decidir por sí misma', 'libertad,respeto', 'key'),
('rc_07', 'Porque las consecuencias afectarían a más gente', 'responsabilidad,cuidado', 'shield'),
('rc_08', 'Porque hay que reparar el daño que se causó', 'responsabilidad,justicia', 'toolbox'),
('rc_09', 'Porque escuchar antes de juzgar es más justo', 'respeto,verdad', 'ear'),
('rc_10', 'Porque la amistad se cuida con hechos, no solo con palabras', 'amistad,confianza', 'heart'),
('rc_11', 'Porque no toda regla vieja sigue teniendo sentido hoy', 'libertad,verdad', 'compass'),
('rc_12', 'Porque pedir ayuda no te hace menos capaz', 'responsabilidad,cuidado', 'handshake'),
('rc_13', 'Porque respetar no es lo mismo que estar de acuerdo', 'respeto,convivencia', 'scale'),
('rc_14', 'Porque lo fácil no siempre es lo correcto', 'responsabilidad,justicia', 'toolbox'),
('rc_15', 'Porque cada persona vive las cosas de forma distinta', 'empatia,respeto', 'ear'),
('rc_16', 'Porque las palabras también pueden hacer daño', 'respeto,cuidado', 'shield'),
('rc_17', 'Porque aprender de un error vale más que ocultarlo', 'verdad,responsabilidad', 'compass'),
('rc_18', 'Porque incluir a todos hace mejor al grupo', 'justicia,convivencia', 'handshake'),
('rc_19', 'Porque mi libertad no puede quitarle la suya a otra persona', 'libertad,respeto', 'key'),
('rc_20', 'Porque confiar merece confianza a cambio', 'confianza,amistad', 'heart'),
('rc_21', 'Porque callar a veces también tiene consecuencias', 'verdad,responsabilidad', 'shield'),
('rc_22', 'Porque las costumbres distintas no son costumbres equivocadas', 'respeto,convivencia', 'ear'),
('rc_23', 'Porque pensarlo dos veces evita arrepentimientos', 'responsabilidad,verdad', 'compass'),
('rc_24', 'Porque el bienestar del grupo también importa, no solo el mío', 'convivencia,cuidado', 'scale');

-- ------------------------------------------------------------
-- Reto de lógica de muestra por cada mecánica (el set completo tiene 18)
-- ------------------------------------------------------------
INSERT INTO logic_challenge (id, islandId, type, prompt, explanation, orderInIsland) VALUES
('logic_verdad_1', 'isla_verdad', 'SEQUENCE', 'Ordena este razonamiento sobre por qué confiar en una fuente.', 'Un buen razonamiento va de la observación a la conclusión.', 0),
('logic_verdad_2', 'isla_verdad', 'MATCH', 'Conecta cada observación con la conclusión que realmente se sigue de ella.', 'Una conclusión válida se apoya solo en lo que la observación permite afirmar.', 1),
('logic_verdad_3', 'isla_verdad', 'SPOT_FLAW', 'Este razonamiento sobre un compañero tiene un fallo. Encuéntralo.', 'El fallo es una generalización apresurada.', 2);

INSERT INTO logic_challenge_item (id, challengeId, text, correctPosition, pairKey, role, isFlawed, displayOrder) VALUES
('logic_verdad_1_item1', 'logic_verdad_1', 'Tres libros distintos cuentan la misma fecha de un evento histórico.', 0, '', '', 0, 0),
('logic_verdad_1_item2', 'logic_verdad_1', 'Las tres fuentes son independientes entre sí.', 1, '', '', 0, 1),
('logic_verdad_1_item3', 'logic_verdad_1', 'Es poco probable que las tres se equivoquen exactamente igual por azar.', 2, '', '', 0, 2),
('logic_verdad_1_item4', 'logic_verdad_1', 'Por eso, esa fecha es una información en la que podemos confiar más.', 3, '', '', 0, 3);

-- ------------------------------------------------------------
-- Insignias (tabla completa: 10)
-- ------------------------------------------------------------
INSERT INTO badge (id, name, description, iconKey, unlockCriteriaKey, sortOrder) VALUES
('badge_primera_pregunta', 'Primer Paso', 'Respondiste tu primera Pregunta del Día.', 'badge_footprint', 'FIRST_QUESTION', 0),
('badge_diez_preguntas', 'Explorador de Ideas', 'Respondiste 10 preguntas filosóficas.', 'badge_compass', 'TEN_QUESTIONS', 1),
('badge_primer_dilema', 'Primer Dilema', 'Resolviste tu primer dilema interactivo.', 'badge_bridge', 'FIRST_DILEMMA', 2),
('badge_dos_miradas', 'Dos Miradas', 'Viste las perspectivas de Lumi y Nox en 5 dilemas distintos.', 'badge_mirror', 'FIVE_DUAL_PERSPECTIVES', 3),
('badge_logica_afilada', 'Mente Afilada', 'Resolviste correctamente 5 retos del Laboratorio de Lógica.', 'badge_gear', 'FIVE_LOGIC_SOLVED', 4),
('badge_debatiente', 'Pequeño Debatiente', 'Completaste tu primer Debate conmigo mismo.', 'badge_scroll', 'FIRST_DEBATE', 5),
('badge_cambio_opinion', 'Mente Abierta', 'Registraste un cambio de opinión en Antes pensaba / Ahora pienso.', 'badge_butterfly', 'FIRST_OPINION_CHANGE', 6),
('badge_isla_verdad', 'Guardián de la Verdad', 'Completaste todo el contenido de la Isla de la Verdad.', 'badge_lighthouse', 'ISLAND_COMPLETE_VERDAD', 7),
('badge_cuaderno', 'Cronista de Ideas', 'Escribiste 5 entradas en tu Cuaderno de Ideas.', 'badge_notebook', 'FIVE_JOURNAL_ENTRIES', 8),
('badge_gran_pensador', 'Gran Pensador de la Isla', 'Completaste al menos una actividad en las 6 islas.', 'badge_crown', 'ALL_ISLANDS_STARTED', 9);

-- Nota: el set completo de contenido (30 preguntas, 12 dilemas, 18 retos de
-- lógica, 12 ejercicios de perspectiva, 6 autodebates) vive tipado en Kotlin
-- bajo app/src/main/java/com/educalab/filosofar/data/local/seed/ y se
-- inserta automáticamente en el primer arranque de la app (DatabaseSeeder).
