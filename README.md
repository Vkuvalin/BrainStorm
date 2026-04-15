# 🧠 BrainStorm (Android / Kotlin / Jetpack Compose)

**BrainStorm** — это Android-реализация культовой интеллектуальной PvP-игры на развитие способностей мозга, в которой два игрока соревнуются в когнитивных мини-играх: на логику, внимание, память, скорость реакции, точность и вычисление. **Проект** воспроизводит весь оригинальный функционал и визуальный стиль оригинального BrainWars, включая онлайн режим, систему микроигр, подсчёт очков, анимации и интерфейс в реальном времени. 
- [Фотогалерея](#фотогалерея)
> Полностью реализовано на **Kotlin + Jetpack Compose**, с применением современных Android-подходов (MVVM, Clean Architecture, Hilt, Coroutines, Navigation Compose).

---

## 🚀 Функциональные особенности

- 🎮 **Мультиплеерные PvP-сессии** (Firebase Realtime)
- 🧩 **10+ когнитивных микроигр** (в проекте реализовал 2 интересные для меня - доделывать/матетизировать и не думал)
- ⚖️ **Система очков и финального результата**
- 🧠 **Тренировка внимания, памяти и реакции**
- 💥 **Анимированный UI (Jetpack Compose Animations)**
- 🎨 **Material You + светлая/тёмная тема**

---

## 🧬 Архитектура проекта

- **MVVM + Clean Architecture**
- **Jetpack Compose** (UI Layer)
- **ViewModel + StateFlow** (State Management)
- **Hilt** (Dependency Injection)
- **Navigation Compose** (безопасная навигация)
- **Coroutines / Flows** (асинхронность)
- **Room / Firebase** (сохранение личной информцации)
- **Animation** (как личная, так и воспроизведенная)
- **MVVM + Clean Architecture** (Модульная структура: ui, domain, data)
- **Адаптивный UI**: планшеты, раскладные экраны.
- **Material You**: светлая и тёмная тема, динамические цвета.
- **Анимации**: переходы между экранами, визуальный фидбек, интерактивные элементы.

---

## 📱 Структура экранов

1. **Splash** — стартовый логотип, авто-переход на главное меню  
2. **Главный экран** — кнопки матчей, рейтинг, профиль  
3. **Matchmaking** — подбор соперника (PvP в реальном времени)  
4. **Раунды 1 → 2 → 3** — микроигры (арифметика, память, реакция)  
5. **Итог матча** — победитель, счёт, аналитика по раундам  
6. **Лидерборд** — локальный рейтинг и между друзьями
7. **Профиль** — уровень, статистика, аватар
8. **Магазин** — покупки кастомизаций и бустов
9. **Настройки** — звук, язык, регистрация и выход из аккаунта, прочие настройки

---

## 🛠️ Технологии и стек

| Категория              | Использовано                                        |
|------------------------|-----------------------------------------------------|
| Язык                   | Kotlin                                              |
| UI-фреймворк           | Jetpack Compose                                     |
| Архитектура            | MVVM + Clean Architecture                           |
| Состояние              | Compose state, ViewModel + Flow                     |
| DI                     | Hilt                                                |
| Асинхронность          | Kotlin Coroutines                                   |
| Навигация              | Navigation Compose                                  |
| Состояние              | ViewModel + StateFlow                               |
| Анимации               | Compose Animations API                              |
| Тестирование           | JUnit, MockK, Compose UI Test                       |
| Хранение               | Room / SharedPreferences                            |
| Сборка                 | Gradle (KTS)                                        |

---

## 🧩 Функциональные возможности

### 🎮 Игровой процесс
- Быстрые PvP-сессии из **3 раундов** с набором когнитивных мини-игр.
- Типы игр:
  - Арифметика и логика
  - Внимание и реакция
  - Кратковременная память
  - Скорость и точность
- Интерактивная шкала очков, реакция на скорость и точность.

### 🧩 Игровая логика
- Асинхронная логика завершения раунда.
- Поддержка оффлайн-режима для тренировки.
- Система подсчёта, анализа, отображения результатов и др.


---

## 💥 Потенциал расширения

- 📊 Firebase Analytics / Crashlytics
- 🌐 Режим онлайн-игры (Firebase Realtime | со сложной логикой)
- 🌍 Мультиязычность (`strings.xml`)
- 🧬 Новые микроигры (добавляются по шаблону)

---

## Фотогалерея

<p float="left">
  <img src="https://github.com/user-attachments/assets/6f02972e-b8c2-49ee-b596-873671a3864d" width="24%" />
  <img src="https://github.com/user-attachments/assets/ee1f81f5-df43-4396-aba5-01919d8db204" width="24%" />
  <img src="https://github.com/user-attachments/assets/12cf9ab3-ba1a-431a-b17b-755003238be6" width="24%" />
  <img src="https://github.com/user-attachments/assets/451ac2d6-6bcb-4bc4-bb40-3ba1d6f14ee2" width="24%" />
</p>

<p float="left">
  <img src="https://github.com/user-attachments/assets/aa5495ee-2208-4de4-a394-47af8f7c437f" width="32%" />
  <img src="https://github.com/user-attachments/assets/2171b1ee-deaa-4792-a4fe-bd09f7c8e5c7" width="32%" />
  <img src="https://github.com/user-attachments/assets/4aaa0e11-4b75-4768-8d96-893c46f2c26a" width="32%" />
</p>

<p float="left">
  <img src="https://github.com/user-attachments/assets/3b71ec8f-8fae-4cd5-8c29-7d561b2b0423" width="32%" />
  <img src="https://github.com/user-attachments/assets/3c9d0615-748d-40b8-80f4-b9559ba192d8" width="32%" />
  <img src="https://github.com/user-attachments/assets/cf65e8b2-b9d4-4c9f-bcad-468de13010aa" width="32%" />
</p>

<p float="left">
  <img src="https://github.com/user-attachments/assets/d4a70380-1a73-4764-bbb6-008e0521bc15" width="32%" />
  <img src="https://github.com/user-attachments/assets/57e42d1e-7b39-4e75-a45a-350eb3bb14e5" width="32%" />
  <img src="https://github.com/user-attachments/assets/4bd145c2-2823-4a4f-8a4a-f2f4cfa12fb4" width="32%" />
</p>

<p float="left">
  <img src="https://github.com/user-attachments/assets/4317e1bc-702c-490f-86bb-ef3905a31a90" width="32%" />
  <img src="https://github.com/user-attachments/assets/1402eb1b-dd2d-4a75-8c71-db30cc58afe0" width="32%" />
  <img src="https://github.com/user-attachments/assets/2cb0459a-e3f5-4652-9716-6c9b5985097f" width="32%" />
</p>

<p float="left">
  <img src="https://github.com/user-attachments/assets/11b935ba-3f84-482f-ae6a-d3724e208e15" width="24%" />
  <img src="https://github.com/user-attachments/assets/d8c463ce-556f-42ee-bc29-9849095e2940" width="24%" />
  <img src="https://github.com/user-attachments/assets/32d5632b-19d6-40d2-bc40-5aea2431990d" width="24%" />
  <img src="https://github.com/user-attachments/assets/a1819905-1239-4447-895b-7d8d2dcd30e9" width="24%" />
</p>
