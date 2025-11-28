# Phase 3: UI + App Polish

## Goal
Build a polished, user-friendly UI with Compose Multiplatform, wire it into the ViewModel, implement offline behavior indicators, and finalize the user experience. Create reusable UI components and ensure the app is production-ready.

---

## Tasks

### 1. Refactor UI into Reusable Components

#### 1.1 Create SearchBar Component
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/presentation/weather/components/SearchBar.kt`
- **Purpose**: Reusable search input component
- **Features**:
  - Text field for city input
  - Search button (or trigger on Enter key)
  - Clear button (X icon) when text is entered
  - Loading state indicator (disable input while loading)
  - Proper Material 3 styling
- **Parameters**:
  - `city: String` - Current input value
  - `onCityChange: (String) -> Unit` - Input change callback
  - `onSearch: () -> Unit` - Search action callback
  - `isLoading: Boolean` - Loading state
  - `enabled: Boolean` - Enable/disable input

#### 1.2 Create WeatherCard Component
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/presentation/weather/components/WeatherCard.kt`
- **Purpose**: Display weather information in a beautiful card
- **Features**:
  - City name header (large, prominent)
  - Temperature display (large, bold, with unit)
  - Weather description (with icon if possible)
  - Humidity and wind speed in organized layout
  - Cache indicator badge/chip (when `isCached = true`)
  - Refresh button (FAB or icon button)
  - Material 3 Card with elevation
  - Proper spacing and typography hierarchy
- **Parameters**:
  - `weather: Weather` - Weather data to display
  - `isCached: Boolean` - Whether data is from cache
  - `onRefresh: () -> Unit` - Refresh action callback
  - `modifier: Modifier` - Optional styling modifier

#### 1.3 Create ErrorState Component
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/presentation/weather/components/ErrorState.kt`
- **Purpose**: Display error messages in a user-friendly way
- **Features**:
  - Error icon
  - Error message text
  - Retry button
  - Different styling for different error types (network, validation, etc.)
  - Material 3 error colors
- **Parameters**:
  - `message: String` - Error message
  - `onRetry: () -> Unit` - Retry action callback
  - `modifier: Modifier` - Optional styling modifier

#### 1.4 Create LoadingState Component
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/presentation/weather/components/LoadingState.kt`
- **Purpose**: Show loading indicator
- **Features**:
  - Circular progress indicator
  - Optional loading text ("Fetching weather...")
  - Centered layout
- **Parameters**:
  - `message: String?` - Optional loading message
  - `modifier: Modifier` - Optional styling modifier

### 2. Enhance WeatherScreen Layout

#### 2.1 Update Main WeatherScreen
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/presentation/weather/WeatherScreen.kt` (refactor from App.kt)
- **Improvements**:
  - Use new component library (SearchBar, WeatherCard, ErrorState, LoadingState)
  - Better layout structure (Column with proper spacing)
  - Handle all state cases properly
  - Show cached indicator when applicable
  - Implement refresh functionality
  - Add empty state (Idle) with helpful message
  - Responsive layout (works on different screen sizes)

#### 2.2 State Handling Logic
- **Idle State**: Show welcome message and search bar
- **Loading State**: Show loading indicator, disable search
- **Success State**: Show WeatherCard with data, enable refresh
- **Error State**: Show ErrorState component with retry option
- **Cached Data**: Show indicator badge/chip on WeatherCard

### 3. Implement Offline UI Behavior

#### 3.1 Cache Indicator
- **Visual Indicator**: 
  - Badge/Chip showing "Cached" or "Offline" when `isCached = true`
  - Different color scheme (e.g., amber/orange) for cached data
  - Icon (cloud-off or similar) to indicate offline data
- **Location**: Display on WeatherCard component

#### 3.2 Offline Error Messages
- **Network Errors**: 
  - Show user-friendly message: "Unable to connect. Showing cached data." (if cache exists)
  - Or: "No internet connection and no cached data available." (if no cache)
- **ErrorState Component**: Update to handle offline scenarios

#### 3.3 Refresh Behavior
- **Refresh Button**: 
  - Add refresh icon button/FAB to WeatherCard
  - When clicked, force network fetch (ignore cache)
  - Show loading state during refresh
  - Update ViewModel to support force refresh

### 4. Add Minimal Styling & Layout

#### 4.1 Material 3 Theming
- **Color Scheme**: 
  - Use Material 3 dynamic colors (if available)
  - Define custom colors for weather states
  - Ensure proper contrast ratios
- **Typography**: 
  - Use Material 3 typography scale
  - Proper hierarchy (headline, title, body)
  - Readable font sizes

#### 4.2 Layout Improvements
- **Spacing**: 
  - Consistent padding (16dp, 24dp)
  - Proper spacing between elements (8dp, 16dp, 24dp)
  - Use `Arrangement.spacedBy()` for consistent gaps
- **Responsive Design**:
  - Max width constraint for larger screens (e.g., 600dp)
  - Center content horizontally
  - Proper vertical spacing

#### 4.3 Visual Polish
- **Card Elevation**: Use appropriate elevation for depth
- **Rounded Corners**: Consistent corner radius (16dp for cards)
- **Icons**: Add icons where appropriate (search, refresh, error, etc.)
- **Animations**: Subtle transitions between states (optional)

### 5. Add Refresh Functionality

#### 5.1 Update ViewModel
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/presentation/weather/WeatherViewModel.kt`
- **Add Method**: 
  - `fun refreshWeather()` - Refresh current weather (if city is known)
  - Or: `fun refreshWeather(city: String)` - Refresh specific city
  - Force network fetch, ignore cache
  - Update state accordingly

#### 5.2 Refresh Button Implementation
- **Location**: WeatherCard component
- **Behavior**:
  - Only show when weather data is displayed
  - Disable during loading
  - Trigger ViewModel refresh method
  - Show loading indicator during refresh

### 6. Final Koin Initialization

#### 6.1 Android Initialization (Verify)
- **File**: `composeApp/src/androidMain/kotlin/com/example/kmp_weather_app/WeatherApplication.kt`
- **Status**: Already implemented, verify it's complete
- **Ensure**:
  - All modules are included (network, repository, use case, presentation, local storage)
  - Android context is provided
  - Koin starts before UI loads

#### 6.2 Desktop Initialization (If Applicable)
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/App.kt` or create desktop-specific init
- **Implementation**:
  - Initialize Koin for desktop (no Android context needed)
  - Use `startKoin { modules(...) }` without `androidContext()`
  - Ensure all modules work on desktop (skip Room module)

#### 6.3 iOS Initialization (If Applicable)
- **File**: iOS app entry point
- **Implementation**:
  - Initialize Koin in iOS app startup
  - Use iOS-specific modules if needed
  - Skip Room module (use placeholder)

### 7. Update App Entry Point

#### 7.1 Refactor App.kt
- **File**: `composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/App.kt`
- **Structure**:
  - `App()` composable - Main entry point with MaterialTheme and KoinContext
  - `WeatherScreen()` - Main weather screen (moved from App.kt)
  - Keep preview functions for development

#### 7.2 Ensure Proper Initialization Order
- Koin must be initialized before `WeatherScreen` tries to inject ViewModel
- For Android: Koin initialized in Application class
- For Desktop/iOS: Initialize in main() or app entry point

### 8. Handle Edge Cases

#### 8.1 Empty City Input
- **Validation**: 
  - Disable search button when input is empty/whitespace
  - Show helpful hint text
  - Prevent API calls with empty strings

#### 8.2 Network Timeout
- **Error Handling**: 
  - Show appropriate timeout message
  - Fallback to cache if available
  - Provide retry option

#### 8.3 Invalid City Name
- **Error Handling**: 
  - Show user-friendly message: "City not found. Please check spelling."
  - Don't clear previous successful results
  - Allow retry with corrected input

#### 8.4 App Startup (No Cache)
- **Initial State**: 
  - Show Idle state with welcome message
  - Don't show error if no cache exists
  - Guide user to search for weather

### 9. Run Full Manual Testing

#### 9.1 Functional Testing
- [ ] **Search Flow**: Enter city → Fetch → Display results
- [ ] **Loading State**: Verify loading indicator appears
- [ ] **Success State**: Verify weather data displays correctly
- [ ] **Error State**: Verify error messages appear
- [ ] **Refresh**: Verify refresh button works
- [ ] **Cache Indicator**: Verify cached badge appears when applicable

#### 9.2 Offline Testing
- [ ] **Online → Offline**: Fetch weather online, turn off network, search again → Shows cached data
- [ ] **Offline First**: Start app offline → Shows error (no cache) or cached data (if exists)
- [ ] **Cache Persistence**: Fetch weather, close app, reopen → Cached weather loads automatically

#### 9.3 Edge Cases Testing
- [ ] **Empty Input**: Try to search with empty city → Button disabled
- [ ] **Invalid City**: Search for non-existent city → Shows error message
- [ ] **Network Timeout**: Simulate timeout → Shows error with retry
- [ ] **Rapid Searches**: Multiple rapid searches → Handles gracefully

#### 9.4 UI/UX Testing
- [ ] **Layout**: Verify layout looks good on different screen sizes
- [ ] **Typography**: Verify text is readable
- [ ] **Colors**: Verify proper contrast and accessibility
- [ ] **Animations**: Verify smooth transitions (if implemented)
- [ ] **Responsiveness**: Verify UI responds to user interactions

#### 9.5 Platform Testing
- [ ] **Android**: Test on Android device/emulator
- [ ] **Desktop** (if applicable): Test on desktop platform
- [ ] **iOS** (if applicable): Test on iOS simulator/device

---

## Deliverable

✅ **Fully working weather app with clean UI, offline support, and polished functionality.**

### Success Criteria
- [ ] UI is broken into reusable components (SearchBar, WeatherCard, ErrorState, LoadingState)
- [ ] All states are properly handled and displayed
- [ ] Offline behavior is clearly indicated to users
- [ ] Refresh functionality works correctly
- [ ] UI is visually polished with Material 3 design
- [ ] Layout is responsive and works on different screen sizes
- [ ] Error handling is user-friendly
- [ ] Koin initialization works on all target platforms
- [ ] App compiles and runs without errors
- [ ] All manual tests pass
- [ ] Code is clean, organized, and maintainable

---

## Technical Notes

### Component Structure Example
```kotlin
// SearchBar.kt
@Composable
fun SearchBar(
    city: String,
    onCityChange: (String) -> Unit,
    onSearch: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = onCityChange,
            label = { Text("City name") },
            enabled = !isLoading,
            modifier = Modifier.weight(1f),
            singleLine = true,
            trailingIcon = {
                if (city.isNotBlank()) {
                    IconButton(onClick = { onCityChange("") }) {
                        Icon(Icons.Default.Clear, "Clear")
                    }
                }
            }
        )
        Button(
            onClick = onSearch,
            enabled = city.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else {
                Text("Search")
            }
        }
    }
}
```

### WeatherCard with Cache Indicator
```kotlin
// WeatherCard.kt
@Composable
fun WeatherCard(
    weather: Weather,
    isCached: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with city name and cache indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weather.cityName,
                    style = MaterialTheme.typography.headlineMedium
                )
                if (isCached) {
                    AssistChip(
                        onClick = {},
                        label = { Text("Cached") },
                        icon = { Icon(Icons.Default.CloudOff, null) }
                    )
                }
            }
            
            // Temperature (large)
            Text(
                text = "${weather.temperature.toInt()}°C",
                style = MaterialTheme.typography.displayMedium
            )
            
            // Description
            Text(
                text = weather.description,
                style = MaterialTheme.typography.titleMedium
            )
            
            // Details row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetail("Humidity", "${weather.humidity}%")
                WeatherDetail("Wind", "${weather.windSpeed} m/s")
            }
            
            // Refresh button
            IconButton(
                onClick = onRefresh,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Refresh, "Refresh")
            }
        }
    }
}
```

### ViewModel Refresh Method
```kotlin
// WeatherViewModel.kt
private var lastSearchedCity: String? = null

fun fetchWeather(city: String) {
    lastSearchedCity = city
    // ... existing implementation
}

fun refreshWeather() {
    lastSearchedCity?.let { city ->
        fetchWeather(city)
    }
}
```

### Koin Desktop Initialization
```kotlin
// For desktop (if applicable)
fun main() {
    startKoin {
        modules(
            networkModule,
            repositoryModule,
            useCaseModule,
            presentationModule
            // Note: Skip localStorageModule for desktop
        )
    }
    
    application {
        App()
    }
}
```

---

## File Structure After Phase 3

```
composeApp/src/commonMain/kotlin/com/example/kmp_weather_app/
├── presentation/
│   └── weather/
│       ├── WeatherViewModel.kt (updated with refresh)
│       ├── WeatherState.kt
│       ├── WeatherScreen.kt (refactored from App.kt)
│       └── components/
│           ├── SearchBar.kt (new)
│           ├── WeatherCard.kt (new)
│           ├── ErrorState.kt (new)
│           └── LoadingState.kt (new)
├── App.kt (refactored, simpler)
└── di/
    └── AppModules.kt
```

---

## UI Design Guidelines

### Material 3 Components
- **Cards**: Use `Card` with elevation for weather display
- **Text Fields**: Use `OutlinedTextField` for search input
- **Buttons**: Use `Button` for primary actions, `IconButton` for refresh
- **Chips**: Use `AssistChip` for cache indicator
- **Icons**: Use Material Icons (search, refresh, error, cloud-off)

### Color Scheme
- **Primary**: Use Material 3 primary color
- **Error**: Use Material 3 error color for error states
- **Surface**: Use Material 3 surface colors for cards
- **Cache Indicator**: Use amber/orange tint for cached data

### Typography Hierarchy
- **Headline**: City name (headlineMedium)
- **Display**: Temperature (displayMedium)
- **Title**: Description (titleMedium)
- **Body**: Details (bodyMedium)

### Spacing
- **Screen Padding**: 16dp or 24dp
- **Card Padding**: 24dp
- **Element Spacing**: 8dp, 16dp, 24dp
- **Max Content Width**: 600dp (for larger screens)

---

## Testing Checklist

### Visual Testing
- [ ] UI looks polished and professional
- [ ] Colors are consistent and accessible
- [ ] Typography is readable and well-hierarchized
- [ ] Spacing is consistent throughout
- [ ] Icons are properly sized and positioned
- [ ] Cards have proper elevation and shadows

### Interaction Testing
- [ ] Search bar responds to input
- [ ] Search button triggers fetch
- [ ] Refresh button triggers refresh
- [ ] Error retry button works
- [ ] Loading states disable appropriate controls
- [ ] Transitions between states are smooth

### Functional Testing
- [ ] All state cases display correctly
- [ ] Cache indicator appears when applicable
- [ ] Offline behavior is clear to users
- [ ] Error messages are user-friendly
- [ ] Empty states are handled gracefully

### Platform Testing
- [ ] Android: Full functionality works
- [ ] Desktop (if applicable): UI adapts properly
- [ ] iOS (if applicable): UI works correctly

---

## Known Considerations

### Icons
- Use Material Icons library if available
- Or use text/emoji as fallback
- Ensure icons are accessible

### Responsive Design
- Test on different screen sizes
- Consider tablet layouts (optional)
- Ensure touch targets are adequate (48dp minimum)

### Accessibility
- Ensure proper contrast ratios
- Add content descriptions for icons
- Test with screen readers (if applicable)

### Performance
- Ensure UI doesn't lag during state transitions
- Optimize recomposition (use `remember` where appropriate)
- Test with slow network connections

---

## Next Steps (Future Enhancements)

After Phase 3 completion, consider:
- **Dark Mode**: Implement dark theme support
- **Search History**: Save and display recent searches
- **Multiple Cities**: Support multiple saved cities
- **Weather Icons**: Add weather condition icons
- **Animations**: Add smooth transitions and animations
- **Unit Tests**: Add UI component tests
- **Accessibility**: Enhance accessibility features

---

## Notes

- **Focus on User Experience**: Make the app intuitive and pleasant to use
- **Keep It Simple**: Don't overcomplicate the UI
- **Material 3**: Follow Material 3 design guidelines
- **Offline First**: Ensure offline experience is clear
- **Error Messages**: Make errors helpful, not technical
- **Polish Matters**: Small details make a big difference

