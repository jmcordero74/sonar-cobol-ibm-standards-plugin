# Contributing to the Project

Thank you for your interest in contributing! 🎉

## How to Contribute

### 1. Reporting Bugs

Open an [issue](https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/issues) with:
- Description of the problem
- Steps to reproduce it
- SonarQube version
- Example COBOL code (if applicable)

### 2. Suggesting Improvements

Open an [issue](https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/issues) with:
- Description of the improvement
- Use case
- Expected benefit

### 3. Submitting Pull Requests

1. **Fork** the repository
2. **Create a branch**: `git checkout -b feature/new-rule`
3. **Make your changes**
4. **Add tests** if possible
5. **Commit**: `git commit -m "Add rule to detect X"`
6. **Push**: `git push origin feature/new-rule`
7. **Open a Pull Request**

### Requirements for PRs

- ✅ Code compiles: `mvn clean package`
- ✅ Tests pass: `mvn test`
- ✅ Clear description of the change
- ✅ Reference to issue (if applicable)

## Local Development

```bash
# Clone
git clone https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin.git
cd sonar-cobol-ibm-standards-plugin

# Compile
mvn clean package

# Run tests
mvn test

# Install in local SonarQube
cp target/sonar-cobol-ibm-standards-plugin-1.0.0.jar $SONARQUBE_HOME/extensions/plugins/
```

## Code of Conduct

- Be respectful
- Accept constructive criticism
- Focus on what is best for the project

## License

By contributing, you agree that your contributions will be licensed under Apache License 2.0.
