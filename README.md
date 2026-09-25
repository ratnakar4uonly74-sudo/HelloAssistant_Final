# Hello Assistant 3.0

Goal:
- Do NOT become the Default Digital Assistant.
- Use the phone's already-selected Digital Assistant.
- Home-screen launcher icon opens the app.
- OPEN DIGITAL ASSISTANT button requests ACTION_ASSIST.
- START — LISTEN FOR HELLO starts a microphone foreground service.
- Say "Hello" and the service requests ACTION_ASSIST.
- STOP LISTENING stops the service.
- Battery/background settings and app settings are provided from the app.

Important:
Android and OEMs can restrict continuous microphone access, especially on the lock screen. SpeechRecognizer is not a dedicated always-on wake-word engine. This build is therefore a functional prototype, not a guarantee of Google-Assistant-style hotword behavior.

Realme setup:
1. Grant microphone permission.
2. Tap START.
3. In Settings, allow background activity / auto-launch if the realme UI exposes those controls.
4. Remove battery restrictions for Hello Assistant.
5. Test unlocked first.
6. Then test with the phone locked.

Never set Hello Assistant as the Default Digital Assistant for this project.
