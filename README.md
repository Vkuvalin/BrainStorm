<div align="center">

<img src="app/src/main/assets/navigation/topappbar1/tab_logo.png" width="160" alt="BrainStorm logo" />

# BrainStorm

**Android-клон культовой интеллектуальной PvP-игры BrainWars.**

Jetpack Compose UI, многослойная навигация, игровые механики, Firebase-интеграция, локальное хранение и анимации.

<img src="https://img.shields.io/badge/Kotlin-Android-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin" />
<img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
<img src="https://img.shields.io/badge/architecture-MVVM%20%2B%20Clean-0A7EA4" alt="MVVM + Clean Architecture" />
<img src="https://img.shields.io/badge/DI-Dagger%202-BD2C00" alt="Dagger 2" />
<img src="https://img.shields.io/badge/Firebase-Auth%20%2B%20Firestore-FFCA28?logo=firebase&logoColor=black" alt="Firebase Auth + Firestore" />
<img src="https://img.shields.io/badge/Android-minSdk%2026%20%7C%20target%2034-3DDC84?logo=android&logoColor=white" alt="Android SDK" />

</div>

---

# 🧠 Обзор

**BrainStorm** — это Android-реализация культовой интеллектуальной PvP-игры на развитие способностей мозга, в которой два игрока соревнуются в когнитивных мини-играх: на логику, внимание, память, скорость реакции, точность и вычисление. **Проект** воспроизводит весь оригинальный функционал и визуальный стиль оригинального BrainWars, включая онлайн режим, систему микроигр, подсчёт очков, анимации и интерфейс в реальном времени. 
- [Фотогалерея](#фотогалерея)

---

## 🚀 Что реализовано

- 🎮 Игровой контур с раундами, подсчётом очков и экраном результатов.
- 🧩 Две полноценно реализованные мини-игры: **Flick Master** и **Path to Safety** *(остальные простые, не стал воспроизводить)*.
- 🧠 Каркас и визуальные ресурсы для дополнительных когнитивных мини-игр BrainWars.
- 👥 Пользовательские профили, друзья, запросы в друзья и чат.
- 🔐 Регистрация, авторизация и восстановление пароля через **Firebase Authentication**.
- ☁️ Синхронизация пользовательских и игровых данных через **Cloud Firestore**.
- 💾 Локальное хранение данных через **Room**.
- 🧭 Многоэкранная навигация на **Navigation Compose**.
- 📊 Игровая и пользовательская статистика.
- 💥 Анимации, интерактивный feedback и звуковое сопровождение.

---

## 🧬 Архитектура

Проект организован по слоям **presentation → domain → data** и использует MVVM-подход:

- **Presentation** — Jetpack Compose screens, ViewModel, UI state.
- **Domain** — entities, repository contracts, use cases.
- **Data** — Room, Firebase, mappers и repository implementation.
- **Dependency Injection** — Dagger 2 с собственными modules/components.
- **Async / state** — Kotlin Coroutines, Flow / StateFlow.

```text
presentation
    ↓
domain (use cases + repository contracts)
    ↓
data (Room + Firebase + mappers)
```

---

## 🛠️ Технологии и стек

| Категория | Использовано |
|---|---|
| Язык | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Архитектура | MVVM + Clean Architecture |
| State management | ViewModel, Compose State, Flow / StateFlow |
| Dependency Injection | Dagger 2 |
| Асинхронность | Kotlin Coroutines |
| Навигация | Navigation Compose |
| Backend services | Firebase Authentication, Cloud Firestore |
| Локальное хранение | Room |
| Сериализация / HTTP-support | Gson, OkHttp logging, Retrofit converter |
| Сборка | Gradle Kotlin DSL (KTS) |
| Android | minSdk 26, targetSdk 34 |
| Test infrastructure | JUnit, AndroidX Test, Compose UI Test |

---

## 🎮 Игровой scope

В репозитории представлены экраны и ресурсы для нескольких режимов BrainWars, но как самостоятельные игровые механики полностью реализованы две мини-игры:

- **Flick Master** — реакция и обработка направления/цвета.
- **Path to Safety** — поиск безопасного пути с собственной игровой логикой.

Остальные режимы оставлены как расширяемый каркас/визуальные заготовки.

---

## 📱 Основные разделы приложения

1. **Splash** — стартовый логотип, авто-переход на главное меню  
2. **Главный экран** — кнопки матчей, рейтинг, профиль  
3. **Matchmaking** — подбор соперника (PvP в реальном времени)  
4. **Раунды 1 → 2 → 3** — микроигры (арифметика, память, реакция)  
5. **Итог матча** — победитель, счёт, аналитика по раундам  
6. **Лидерборд** — локальный рейтинг и между друзьями
7. **Профиль** — уровень, пользовательская информация, статистика, аватар
8. **Настройки** — звук, язык, регистрация и выход из аккаунта, прочие настройки

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
