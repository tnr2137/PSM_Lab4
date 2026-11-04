# Programowanie Systemów Mobilnych — Kalkulator (Część 1)

Aplikacja mobilna kalkulatora z elastycznym interfejsem dla orientacji pionowej i poziomej (z funkcjami naukowymi), zbudowana od zera w **Android Studio Quail 4 (2026.1.4)** dla **Android 17 (API 37)** z wykorzystaniem **Jetpack Compose** i **Material 3 Design**.

---

## 📋 Cel Projektu i Zrealizowane Wymagania

Projekt realizuje wytyczne z **Konspektu 4 (Kalkulator - część 1)**. Główne założenia i zrealizowane punkty obejmują:

1. **Brak blokady orientacji (Zadanie 1):**
   - Brak atrybutu `android:screenOrientation` w manifeście. Aplikacja płynnie dostosowuje się do obrotu ekranu w emulatorze oraz na urządzeniach typu tablet / składak (API 37 / targetSdk 37).

2. **Układ pionowy i elastyczna siatka (Zadanie 2):**
   - **Wyświetlacz:** Pole wyświetlające wyrażenie z wyrównaniem do prawej strony oraz obsługą przewijania poziomego (`horizontalScroll`), dzięki czemu długie wyrażenia nie znikają za krawędzią ekranu.
   - **Siatka przycisków:** Skaluje się z rozmiarem ekranu bez sztywno wpisanych rozmiarów w `dp` (wykorzystanie `Modifier.weight`, `aspectRatio` oraz `Arrangement.spacedBy`).
   - **Model danych przycisku:** Użycie klasy `CalcButton` oraz enumeracji `ButtonCategory`, pozwalające uniknąć powielania wywołań w kodzie.

3. **Układ poziomy i tryb naukowy (Zadanie 3):**
   - W orientacji poziomej (`ORIENTATION_LANDSCAPE`), obok podstawowego bloku kalkulatora odsłania się dodatkowy blok funkcji naukowych (`sin(`, `cos(`, `tan(`, `pi`, `sqrt(`, `^`, `ln(`, `e`).
   - **Brak duplikacji kodu:** Zarówno blok podstawowy, jak i naukowy są budowane przez ten sam komponent `@Composable fun Keypad`, wywoływany z różnymi danymi.
   - **Wpisywanie symboli:** Przycisk `pi` wpisuje nazwę stałej `pi`, a funkcje wpisują nazwę razem z nawiasem otwierającym (np. `sin(`), przygotowując aplikację pod przyszły silnik obliczeniowy w Części 2.

4. **Własna kolorystyka i obsługa motywów (Zadanie 4):**
   - Przygotowano własną, świadomie dobraną paletę barw w systemie Material 3 (`ui/theme/Color.kt` i `ui/theme/Theme.kt`).
   - Wyraźne rozróżnienie wizualne ról przycisków (cyfry, operatory, akcje, przycisk równania `=`).
   - Pełna obsługa trybu jasnego i ciemnego (**Light & Dark Theme**) z zachowaniem wytycznych dostępności WCAG 2.2 odnośnie kontrastu ($\ge 4.5:1$).

---

## 📝 Odpowiedzi do Sprawozdania

### 1. Dlaczego przycisk $\pi$ powinien wpisywać nazwę stałej, a nie liczbę 3,14159?
Wpisywanie przybliżonej wartości liczbowej (np. `3.14159`) wprowadza wczesny błąd zaokrąglenia już na etapie budowania wyrażenia. Wpisywanie identyfikatora/symbolu stałej (`pi`) pozwala silnikowi obliczeniowemu (w Części 2) na użycie pełnej precyzji zmiennoprzecinkowej oferowanej przez system (np. `Math.PI` w typie `Double`) lub na przetwarzanie symboliczne, zapobiegając utracie dokładności przy złożonych obliczeniach.

### 2. Uzasadnienie wyboru koloru bazowego i ról kolorystycznych
Paleta barw została wygenerowana i dobrana w oparciu o stonowaną, nowoczesną tonację ciepłego brązu i beżu (*Warm Earth / Stone*):
- **Cyfry (`NUMBER`):** Neutralny kontener powierzchniowy (`surfaceContainerHigh`), czytelny i niemęczący wzroku.
- **Operatory (`OPERATOR`):** Ciepły brązowy odcień (`secondaryContainer`), pozwalający na szybkie odróżnienie działań od cyfr.
- **Akcje (`ACTION` - np. `C`, `⌫`, nawiasy):** Jasny/ciemny stonowany beż (`tertiaryContainer`), podkreślający przyciski funkcyjne.
- **Przycisk równości (`EQUALS`):** Akcentujący kolor pomarańczowy (`primary`), jednoznacznie wyróżniający kluczową akcję wykonania obliczenia.
- Wybrany kolor bazowy zapewnia wysoki kontrast tekstu względem tła we wszystkich kategoriach oraz bezproblemowe przełączanie między motywem jasnym i ciemnym.

---

## 🛠 Struktura Projektu i Główne Komponenty

```text
app/src/main/java/com/example/kalkulator/
├── MainActivity.kt                # Główna aktywność i logika UI (CalculatorScreen, Keypad, Display, Button)
└── ui/theme/
    ├── Color.kt                   # Definicje barw dla trybu jasnego i ciemnego (Light & Dark ColorScheme)
    └── Theme.kt                   # Konfiguracja KalkulatorTheme (Material3 Theme setup)
```

---

## 🚀 Uruchomienie Projektu

1. Otwórz projekt w **Android Studio Quail 4 (2026.1.4)** lub nowszym.
2. Zbuduj projekt korzystając z Gradle Sync / Build (`app`).
3. Uruchom aplikację na emulatorze z Androidem 17 (API 37) lub na urządzeniu fizycznym.
4. Przestaw orientację urządzenia/emulatora, aby przetestować przełączanie pomiędzy trybem pionowym (kalkulator podstawowy) i poziomym (kalkulator naukowy).
