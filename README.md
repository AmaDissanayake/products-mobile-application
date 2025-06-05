# Products Mobile Application

## Features
- **Splash Screen**: Branded launch experience with theme adaptation
- **Product List**: Paginated product listing with lazy loading
- **Product Details**: Interactive image gallery with tags display
- **Error Handling**: Graceful degradation on network failures
- **Modern UI**: Material 3 design with dark/light theme support

## Technical Approach
- **Architecture**: MVVM with clean separation of concerns
- **UI**: Jetpack Compose with Material 3 components
- **Networking**: Retrofit with Gson conversion
- **Image Loading**: Coil for efficient image handling
- **Navigation**: Jetpack Navigation Compose
- **Dependency Management**: Manual DI with ViewModelFactory

## Trade-offs
- **Offline Support**: Not implemented due to time constraints
- **Complex State**: Chose simplicity over reactive streams (RxJava/Flow)
- **Image Library**: Used Coil instead of Glide for Compose compatibility
- **Caching**: Memory caching only (no persistent storage)

## Testing
- **Unit Tests**: 
  - ViewModels (ProductListVM, ProductDetailVM)
  - Repositories (ProductRepo, ProductDetailRepo)
  - Model parsing (Product, ProductsResponse)
- **Integration**: ViewModel + Repository tests
- **Coverage**: Core business logic fully tested

## How to Run
1. Clone repository
2. Open in Android Studio (Giraffe or newer)
3. Build and run on emulator/device (API 24+)

## Dependencies
- Jetpack Compose
- Retrofit 2
- Coil
- ViewModel
- Navigation Compose
- JUnit/MockK for testing