# ⚠️ LEGAL GUIDE - What to Avoid in the Plugin

## ❌ WHAT YOU SHOULD NEVER SAY OR DO

### 1. **DO NOT Imply Official Affiliation**

❌ **NEVER say:**
- "Official IBM plugin"
- "Approved by IBM"
- "Certified by IBM"
- "Developed by IBM"
- "In collaboration with IBM"
- "Sponsored by Sonarsource"
- "Official Sonarsource plugin"

✅ **You CAN say (as you already do):**
- "Community plugin"
- "Independent project"
- "Not affiliated with IBM or Sonarsource"
- "Based on IBM public documentation"

### 2. **DO NOT Use Logos or Trademarks**

❌ **NEVER include:**
- IBM logo
- Sonarsource logo
- SonarQube logo
- Any corporate imagery from these companies

✅ **You CAN:**
- Mention names in text (descriptive use)
- Include trademark disclaimers (as you already do)

### 3. **DO NOT Copy Proprietary Code**

❌ **NEVER include:**
- IBM compiler source code
- IBM proprietary libraries
- IBM COBOL runtime
- Commercial Sonarsource plugin code

✅ **You CAN:**
- Analyze public COBOL syntax
- Reference public documentation
- Detect syntactic patterns

### 4. **DO NOT Make Warranty Claims**

❌ **NEVER say:**
- "Guarantees 100% IBM compatibility"
- "Certified for production"
- "Approved for enterprise use"
- "Bug-free guaranteed"

✅ **You CAN say:**
- "Provided 'AS IS' without warranties" (already in LICENSE)
- "Community plugin for analysis"
- "Use at your own risk"

### 5. **DO NOT Offer Official Support**

❌ **NEVER say:**
- "Official IBM support"
- "Contact IBM for support"
- "24h response guarantee"

✅ **You CAN:**
- "Community support via GitHub Issues"
- "Pull requests welcome"
- "Best effort, no guarantees"

### 6. **DO NOT Modify the Disclaimer**

❌ **NEVER remove or change:**
```
This plugin is a community project, independent and unofficial.
It is not affiliated with, sponsored by, or approved by IBM or Sonarsource.
"IBM" and "IBM COBOL" are trademarks of International Business Machines Corporation.
"SonarQube" is a trademark of Sonarsource SA.
```

✅ **Always keep it visible in:**
- README.md
- pom.xml (description)
- LICENSE

### 7. **DO NOT Use Misleading Names**

❌ **NEVER name the repo:**
- `ibm-official-cobol-plugin`
- `sonarsource-cobol-plugin`
- `ibm-certified-sonar-plugin`

✅ **You CAN use (as you already do):**
- `sonar-cobol-ibm-standards-plugin` ✅
- Clearly indicates it detects "IBM standards"
- Descriptive use, does not imply affiliation

## ✅ WHAT YOU CAN DO (As you already do)

### 1. **Descriptive Use of Trademarks**
✅ "Detects IBM COBOL extensions"
✅ "Based on IBM documentation"
✅ "Plugin for SonarQube"

### 2. **References to Public Documentation**
✅ Cite: https://www.ibm.com/docs/en/cobol-linux-x86/1.2.0
✅ Mention public standards (ANSI X3.23-1985)

### 3. **Analysis of Public Syntax**
✅ Detect COBOL keywords
✅ Analyze code structure
✅ Identify syntactic patterns

### 4. **Open Source License**
✅ Apache License 2.0
✅ Open source on GitHub
✅ Free use, modification, and distribution

## 📋 CHECKLIST BEFORE PUBLISHING

Verify your project has:

- ✅ Visible disclaimer in README
- ✅ Disclaimer in pom.xml
- ✅ LICENSE with Apache 2.0
- ✅ Trademark acknowledgment
- ✅ No IBM or Sonarsource logos
- ✅ No proprietary code
- ✅ Descriptive (not misleading) name
- ✅ "Community" or "Independent" in description

## 🛡️ LEGAL PROTECTION

Your plugin is protected by:

1. **Fair Use**: Educational/informational use of public documentation
2. **Nominative Use**: Descriptive use of trademarks to identify technology
3. **Apache License 2.0**: Permissive and widely recognized license
4. **Disclaimers**: Clear and visible

## 📞 IF YOU RECEIVE A LEGAL NOTICE

**Steps to follow:**

1. **Don't panic** — It is very unlikely
2. **Read carefully** the notice
3. **Verify it is legitimate** (not spam/phishing)
4. **Respond politely** stating:
   - It is a community project
   - Descriptive use of trademarks (legal)
   - Based on public documentation
   - Willing to make changes if necessary
5. **Consult a lawyer** if needed

## 🎯 SUMMARY

**Your plugin is legally correct because:**

✅ Has clear disclaimers
✅ Does not imply affiliation
✅ Uses trademarks descriptively
✅ Based on public documentation
✅ No proprietary code
✅ Compatible license

**Legal risk: MINIMAL**

Follow these guidelines and you will be protected. Publish with confidence! 🚀

---

**Last updated**: 2024
**Author**: jmcordero74
**License**: Apache 2.0
