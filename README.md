# One Minute Countdown
This is an assignment project. Create a new Android project using Jetpack Compose (including a ViewModel) that shows a 1-minute countdown including seconds and milliseconds.
## Description
1. One label that shows the current timer value
2. Two buttons that can be clicked to start/pause and stop the timer
3. The view shows an animated ring around the text and the button that shows the current state of the timer
4. The timer should work correctly when the app is in the background
5. The ViewModel should use Flows / StateFlows for the implementation of the logic
6. The ViewModel should be created by dependency injection with Koin
7. The app should post a system notification when the timer ends if the app is in the background
