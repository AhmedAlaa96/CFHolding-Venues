# CF-Holding Venues

## Features

- Registration of new users with required fields (First Name, Last Name, Age, Email, Password)
- Login functionality with email/password authentication and validation
- Dashboard with side menu including Home (default), My Profile, Terms & Conditions, and Logout
- Home screen displaying nearby venues using latitude and longitude
- Integration with Foursquare API to fetch nearby venues
- Display of venue information including name, location, and category
- Option to view venues in list view or on Google Maps with markers
- User profile displaying name, age, and email
- Display of random terms and conditions
- Logout functionality to log out the user and redirect to the login screen

## Installation

1. Clone the repository from GitHub: `git clone https://github.com/AhmedAlaa96/CFHolding-Venues.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or a physical device.

## Prerequisites

- Android Studio (version Android Studio Iguana | 2023.2.1 Patch 1 or higher)

## Technologies Used

- Kotlin
- Android Architecture Components (ViewModel, Flow Coroutines)
- Retrofit (for network requests)
- Room (for local storage)
- Glide (for image loading)
- Dependency Injection (Hilt)

## Project Structure

The project follows the MVVM (Model-View-ViewModel) architecture pattern, combined with clean architecture principles. It is structured as follows:

- `app`: Contains the main Android application module, including activities, fragments, and UI-related code.
- `data`: Handles data-related operations, including data fetching, caching, and storage.
- `domain`: Defines the business logic and use cases of the application.
- `presentation`: Contains the ViewModels and UI-related logic for the app.
- `utils`: Contains utility classes and helper functions used throughout the project.

## Usage

1. Launch the app on your Android device or emulator.
2. Register or login with your email and password.
3. The app will display nearby venues on the home screen.
4. Switch between list view and Google Maps view using the toggle at the top.
5. Tap on a venue to view more details.
6. Navigate to My Profile to view your personal information.
7. Explore the Terms & Conditions section.
8. Logout from the app when done.

## Release History

- v1.0 (2024-04-03): Initial release
    - Implemented basic functionality including user authentication and venue display.

### You can find the latest build [here ↗](https://install.appcenter.ms/users/ahmedalaa/apps/cf-holding-venues/distribution_groups/public)

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please submit an issue or create a pull request.

## Acknowledgments

- [Foursquare API ↗](https://developer.foursquare.com/) - API used for fetching nearby venues.

## Contact

If you have any questions or inquiries, please contact:

Ahmed Alaa\
Email: ahmedalaahussein00@gmail.com\
LinkedIn: [Ahmed Alaa ↗](https://www.linkedin.com/in/ahmed-alaa-hussein/)
