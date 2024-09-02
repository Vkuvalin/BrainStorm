package com.kuvalin.brainstorm.presentation.viewmodels.achievement

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.entity.Achievement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AchievementsViewModel @Inject constructor(): ViewModel() {

    private val initialAchievementList = listOf(
        Achievement("ic_3000 points.png", "3000 баллов", "Наберите более 3000 баллов за одну игру.", true),
        Achievement("ic_knowledge_base.png", "База знаний", "Достигните более 950 очков навыка 'память'.", false),
        Achievement("ic_accuracy.png", "Не промахнусь", "Достигните более 950 очков навыка 'точность'.", false),
        Achievement("ic_thinking.png", "Сама логика", "Достигните более 950 очков навыка 'суждение'.", false),
        Achievement("ic_calculate.png", "Калькулятор", "Достигните более 950 очков навыка 'вычисление'.", false),
        Achievement("ic_deft.png", "Не уйдешь", "5 раз вырвите победу в последней игре.", false),
        Achievement("ic_invincible.png", "Непобедимый", "Выиграйте 10 игр подряд.", true),
        Achievement("ic_observation.png", "Не скроешься", "Достигните более 950 очков навыка 'наблюдательность'.", false),
        Achievement("ic_conqueror.png", "Покоритель", "Доберитесь до S лиги.", false),
        Achievement("ic_speed.png", "Флеш не ровня", "Достигните более 950 очков навыка 'скорость'.", false),
        Achievement("ic_top_1.png", "Топ 1", "Займите 1-е место в лиге.", true)
    )

    private val _achievementList = MutableStateFlow<List<Achievement>>(initialAchievementList)
    val achievementList: StateFlow<List<Achievement>> = _achievementList


    // TODO я не наполнял базу, поэтому  использовать не буду
    fun updateAchievementState(title: String, newState: Boolean) {
        _achievementList.value = _achievementList.value.map {achievement ->
            if (achievement.title == title) achievement.copy(activeState = newState)
            else achievement
        }
    }



}