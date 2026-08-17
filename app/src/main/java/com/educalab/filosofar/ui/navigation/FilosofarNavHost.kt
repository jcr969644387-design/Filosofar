package com.educalab.filosofar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.educalab.filosofar.AppContainer
import com.educalab.filosofar.ui.screens.beforeafter.OpinionRevisionScreen
import com.educalab.filosofar.ui.screens.beforeafter.OpinionRevisionViewModel
import com.educalab.filosofar.ui.screens.dailyquestion.DailyQuestionScreen
import com.educalab.filosofar.ui.screens.dailyquestion.DailyQuestionViewModel
import com.educalab.filosofar.ui.screens.dilemmas.DilemmaDetailScreen
import com.educalab.filosofar.ui.screens.dilemmas.DilemmaDetailViewModel
import com.educalab.filosofar.ui.screens.dilemmas.DilemmaListScreen
import com.educalab.filosofar.ui.screens.dilemmas.DilemmaListViewModel
import com.educalab.filosofar.ui.screens.journal.JournalScreen
import com.educalab.filosofar.ui.screens.journal.JournalViewModel
import com.educalab.filosofar.ui.screens.logiclab.LogicChallengeScreen
import com.educalab.filosofar.ui.screens.logiclab.LogicChallengeViewModel
import com.educalab.filosofar.ui.screens.logiclab.LogicLabListScreen
import com.educalab.filosofar.ui.screens.logiclab.LogicLabViewModel
import com.educalab.filosofar.ui.screens.map.IslandDetailScreen
import com.educalab.filosofar.ui.screens.map.IslandDetailViewModel
import com.educalab.filosofar.ui.screens.map.MapScreen
import com.educalab.filosofar.ui.screens.map.MapViewModel
import com.educalab.filosofar.ui.screens.onboarding.OnboardingScreen
import com.educalab.filosofar.ui.screens.perspective.PerspectiveDetailScreen
import com.educalab.filosofar.ui.screens.perspective.PerspectiveDetailViewModel
import com.educalab.filosofar.ui.screens.perspective.PerspectiveListScreen
import com.educalab.filosofar.ui.screens.perspective.PerspectiveListViewModel
import com.educalab.filosofar.ui.screens.profile.ProfileSetupScreen
import com.educalab.filosofar.ui.screens.profile.ProfileViewModel
import com.educalab.filosofar.ui.screens.progress.ProgressScreen
import com.educalab.filosofar.ui.screens.progress.ProgressViewModel
import com.educalab.filosofar.ui.screens.reasoncards.ReasonCardsScreen
import com.educalab.filosofar.ui.screens.reasoncards.ReasonCardsViewModel
import com.educalab.filosofar.ui.screens.selfdebate.SelfDebateDetailScreen
import com.educalab.filosofar.ui.screens.selfdebate.SelfDebateDetailViewModel
import com.educalab.filosofar.ui.screens.selfdebate.SelfDebateListScreen
import com.educalab.filosofar.ui.screens.selfdebate.SelfDebateListViewModel
import com.educalab.filosofar.ui.screens.settings.SettingsScreen
import com.educalab.filosofar.util.AudioRecorderManager
import com.educalab.filosofar.util.ViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun FilosofarNavHost(container: AppContainer) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val profileState = container.userProfileRepository.observeProfile().collectAsState(initial = null)
    var seeded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        container.seeder.seedIfNeeded()
        seeded = true
    }

    if (!seeded) return

    val startDestination = if (profileState.value?.onboardingCompleted == true) Routes.MAP else Routes.ONBOARDING

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.ONBOARDING) {
            OnboardingScreen(onFinished = { navController.navigate(Routes.PROFILE_SETUP) { popUpTo(Routes.ONBOARDING) { inclusive = true } } })
        }

        composable(Routes.PROFILE_SETUP) {
            val vm: ProfileViewModel = viewModel(factory = ViewModelFactory(container) { ProfileViewModel(it.userProfileRepository) })
            ProfileSetupScreen(vm) {
                navController.navigate(Routes.MAP) { popUpTo(Routes.PROFILE_SETUP) { inclusive = true } }
            }
        }

        composable(Routes.MAP) {
            val vm: MapViewModel = viewModel(factory = ViewModelFactory(container) { MapViewModel(it.progressRepository, it.userProfileRepository) })
            MapScreen(
                viewModel = vm,
                onOpenIsland = { islandId -> navController.navigate(Routes.islandDetail(islandId)) },
                onOpenProgress = { navController.navigate(Routes.PROGRESS_COLLECTION) },
                onOpenJournal = { navController.navigate(Routes.JOURNAL) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                onOpenOpinionRevision = { navController.navigate(Routes.OPINION_REVISION) }
            )
        }

        composable(Routes.SETTINGS) {
            val vm: ProfileViewModel = viewModel(factory = ViewModelFactory(container) { ProfileViewModel(it.userProfileRepository) })
            SettingsScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.ISLAND_DETAIL, arguments = listOf(navArgument("islandId") { type = NavType.StringType })) { entry ->
            val islandId = entry.arguments!!.getString("islandId")!!
            val vm: IslandDetailViewModel = viewModel(factory = ViewModelFactory(container) { IslandDetailViewModel(islandId, it.progressRepository) })
            IslandDetailScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onOpenQuestion = { navController.navigate(Routes.dailyQuestion(islandId)) },
                onOpenDilemmas = { navController.navigate(Routes.dilemmaList(islandId)) },
                onOpenPerspectives = { navController.navigate(Routes.perspectiveList(islandId)) },
                onOpenLogicLab = { navController.navigate(Routes.logicLab(islandId)) },
                onOpenDebates = { navController.navigate(Routes.selfDebateList(islandId)) }
            )
        }

        composable(Routes.DAILY_QUESTION, arguments = listOf(navArgument("islandId") { type = NavType.StringType })) { entry ->
            val islandId = entry.arguments!!.getString("islandId")!!
            val vm: DailyQuestionViewModel = viewModel(factory = ViewModelFactory(container) { DailyQuestionViewModel(islandId, it.dailyQuestionRepository) })
            DailyQuestionScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.DILEMMA_LIST, arguments = listOf(navArgument("islandId") { type = NavType.StringType })) { entry ->
            val islandId = entry.arguments!!.getString("islandId")!!
            val vm: DilemmaListViewModel = viewModel(factory = ViewModelFactory(container) { DilemmaListViewModel(islandId, it.dilemmaRepository) })
            DilemmaListScreen(vm, onBack = { navController.popBackStack() }, onOpenDilemma = { navController.navigate(Routes.dilemmaDetail(it)) })
        }

        composable(Routes.DILEMMA_DETAIL, arguments = listOf(navArgument("dilemmaId") { type = NavType.StringType })) { entry ->
            val dilemmaId = entry.arguments!!.getString("dilemmaId")!!
            val vm: DilemmaDetailViewModel = viewModel(factory = ViewModelFactory(container) { DilemmaDetailViewModel(dilemmaId, it.dilemmaRepository) })
            DilemmaDetailScreen(vm, onBack = { navController.popBackStack() }, onGoToReasonCards = { navController.navigate(Routes.reasonCards(dilemmaId)) })
        }

        composable(Routes.REASON_CARDS, arguments = listOf(navArgument("dilemmaId") { type = NavType.StringType })) {
            val vm: ReasonCardsViewModel = viewModel(factory = ViewModelFactory(container) { ReasonCardsViewModel(it.reasonCardRepository) })
            ReasonCardsScreen(vm) { navController.popBackStack(Routes.MAP, inclusive = false) }
        }

        composable(Routes.PERSPECTIVE_LIST, arguments = listOf(navArgument("islandId") { type = NavType.StringType })) { entry ->
            val islandId = entry.arguments!!.getString("islandId")!!
            val vm: PerspectiveListViewModel = viewModel(factory = ViewModelFactory(container) { PerspectiveListViewModel(islandId, it.perspectiveRepository) })
            PerspectiveListScreen(vm, onBack = { navController.popBackStack() }, onOpen = { navController.navigate(Routes.perspectiveDetail(it)) })
        }

        composable(Routes.PERSPECTIVE_DETAIL, arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })) { entry ->
            val exerciseId = entry.arguments!!.getString("exerciseId")!!
            val vm: PerspectiveDetailViewModel = viewModel(factory = ViewModelFactory(container) { PerspectiveDetailViewModel(exerciseId, it.perspectiveRepository) })
            PerspectiveDetailScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.LOGIC_LAB, arguments = listOf(navArgument("islandId") { type = NavType.StringType })) { entry ->
            val islandId = entry.arguments!!.getString("islandId")!!
            val vm: LogicLabViewModel = viewModel(factory = ViewModelFactory(container) { LogicLabViewModel(islandId, it.logicRepository) })
            LogicLabListScreen(vm, onBack = { navController.popBackStack() }, onOpen = { navController.navigate(Routes.logicChallenge(it)) })
        }

        composable(Routes.LOGIC_CHALLENGE, arguments = listOf(navArgument("challengeId") { type = NavType.StringType })) { entry ->
            val challengeId = entry.arguments!!.getString("challengeId")!!
            val vm: LogicChallengeViewModel = viewModel(factory = ViewModelFactory(container) { LogicChallengeViewModel(challengeId, it.logicRepository) })
            LogicChallengeScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.SELF_DEBATE_LIST, arguments = listOf(navArgument("islandId") { type = NavType.StringType })) { entry ->
            val islandId = entry.arguments!!.getString("islandId")!!
            val vm: SelfDebateListViewModel = viewModel(factory = ViewModelFactory(container) { SelfDebateListViewModel(islandId, it.selfDebateRepository) })
            SelfDebateListScreen(vm, onBack = { navController.popBackStack() }, onOpen = { navController.navigate(Routes.selfDebateDetail(it)) })
        }

        composable(Routes.SELF_DEBATE_DETAIL, arguments = listOf(navArgument("debateId") { type = NavType.StringType })) { entry ->
            val debateId = entry.arguments!!.getString("debateId")!!
            val vm: SelfDebateDetailViewModel = viewModel(factory = ViewModelFactory(container) { SelfDebateDetailViewModel(debateId, it.selfDebateRepository) })
            SelfDebateDetailScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.OPINION_REVISION) {
            val vm: OpinionRevisionViewModel = viewModel(factory = ViewModelFactory(container) { OpinionRevisionViewModel(it.dailyQuestionRepository, it.reflectionRepository) })
            OpinionRevisionScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.JOURNAL) {
            val audioManager = remember { AudioRecorderManager(context) }
            val vm: JournalViewModel = viewModel(factory = ViewModelFactory(container) { JournalViewModel(it.reflectionRepository, audioManager) })
            JournalScreen(vm) { navController.popBackStack() }
        }

        composable(Routes.PROGRESS_COLLECTION) {
            val vm: ProgressViewModel = viewModel(factory = ViewModelFactory(container) { ProgressViewModel(it.progressRepository) })
            ProgressScreen(vm) { navController.popBackStack() }
        }
    }
}
