![Logo][logo]

# Biometric Speech Recognition System

![Language][language-url] ![License][license-url] ![Version][version-url] ![Done][done-url] ![Maintenance][maintenance-url] 

### A biometric system for person identification by voice recognition

##### This project was developed for *Bases of Biometry* classes at Electronics, Telecommunications and Informatics faculty of Gdańsk University of Technology. It took approximately 2 days to accomplish. Although three people participated in the project, the software was made only by me.

## How it works

- Firstly, the user must register by filling first name and last name input fields and then clicking the *REGISTER VOICE SAMPLE* button.
- Then the text appears and user must read it.
- After reading the text user must click the *FINISH* button.

From this moment user is registered in the system (a file with recorded voice sample is saved in project file system - `sounds` folder).

- User identification can be done by clicking the *CHECK VOICE* button.
- Text to read appears.
- User reads the text and clicks the *FINISH* button.
- Information about identified person and its propability is presented to the user.

Fragments of *The Snow Queen* by **Hans Christian Andersen** (Polish translation) was used as a text. Feel free to change that in `text/text.txt` file

## Screenshots

<img src="screenshots/main.PNG" alt="Main view" width="250" height="314"> <img src="screenshots/registration.PNG" alt="Registration" width="250" height="314"> <img src="screenshots/identification.PNG" alt="Identification" width="250" height="314">

## Demo

<a href="http://www.youtube.com/watch?feature=player_embedded&v=oWzyNVCM_hY" target="_blank"><img src="http://img.youtube.com/vi/oWzyNVCM_hY/0.jpg" alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" />
</a>

## Used libraries:

- [Recognito : Text Independent Speaker Recognition in Java](https://github.com/amaurycrickx/recognito) | Apache License 2.0

[logo]: images/logo.png "Biometric Speech Recognition System"
[license-url]: https://img.shields.io/badge/license-MIT-blue.svg?style=flat "License"
[version-url]: https://img.shields.io/badge/version-1.0.0-brightgreen.svg?style=flat "Version"
[maintenance-url]: https://img.shields.io/maintenance/no/2015.svg?style=flat "Maintenance"
[language-url]: https://img.shields.io/badge/language-Java-lightgrey.svg?style=flat "Language"
[done-url]: https://img.shields.io/badge/done-11.2015-yellow.svg?style=flat "Done"
