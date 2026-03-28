# ✅ Checklist for Publishing on GitHub

## 🔴 CRITICAL - Must be done before publishing

- [ ] **Change the language KEY from "cobol" to "cobol-ibm"** to avoid conflicts
  - Files to modify:
    - `src/main/java/com/plugin/cobol/language/CobolLanguage.java`
    - `src/main/java/com/plugin/cobol/rules/CobolRulesDefinition.java`
    - `src/main/java/com/plugin/cobol/language/CobolQualityProfile.java`
    - `src/main/java/com/plugin/cobol/CobolSensor.java`
    - `src/main/java/com/plugin/cobol/cpd/CobolCpdSensor.java` (if it exists)

- [ ] **Unify artifact names**
  - Change `artifactId` in pom.xml to: `sonar-cobol-ibm-standards-plugin`
  - Change `pluginKey` to: `cobol-ibm-standards`
  - Update README.md with the correct JAR name

- [ ] **Update URLs in pom.xml**
  - Uncomment and update `<url>` with your GitHub repository
  - Uncomment and update `<pluginUrl>` in the plugin configuration

## ⚠️ IMPORTANT - Recommended before publishing

- [ ] **Add developer information in pom.xml**
  ```xml
  <developers>
      <developer>
          <name>Your Name</name>
          <email>your@email.com</email>
      </developer>
  </developers>
  ```

- [ ] **Update README.md**
  - Replace `YOUR_USER` with your actual GitHub username
  - Add badges (license, build status)
  - Verify all commands are correct

- [ ] **Create CHANGELOG.md**
  - Document initial version 1.0.0

- [ ] **Verify LICENSE**
  - Update year if necessary
  - Add your name/organization to Copyright

## ℹ️ OPTIONAL - Future improvements

- [ ] Add unit tests
- [ ] Create CONTRIBUTING.md
- [ ] Add code examples in rule descriptions
- [ ] Configure GitHub Actions for CI/CD
- [ ] Create automated releases
- [ ] Add extended documentation in Wiki

## 📝 Final verification

- [ ] Compile: `mvn clean package`
- [ ] Verify that the JAR is generated correctly
- [ ] Test installation on local SonarQube
- [ ] Verify that the 71 rules appear in SonarQube
- [ ] Analyze a test COBOL project
- [ ] Verify there are no conflicts with other plugins

## 🚀 Publication

- [ ] Create repository on GitHub
- [ ] Push the code
- [ ] Create release v1.0.0
- [ ] Attach JAR to the release
- [ ] Announce in COBOL/SonarQube communities
