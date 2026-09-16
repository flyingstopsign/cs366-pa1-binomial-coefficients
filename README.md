# CS366 — PA01: Binomial Coefficients

Starter project for Programming Assignment 01. Implement and experimentally compare three Java methods for computing `C(n, k)`:

- the direct factorial definition;
- cancellation of shared factorial terms; and
- direct recursion using Pascal's identity.

The complete assignment handout and due date are posted on Kodiak. Kodiak is authoritative if this README and the handout ever differ.

## Work in a Codespace

Open this repository on GitHub, choose **Code**, select **Codespaces**, and create a Codespace. The included dev-container configuration supplies Java 21. A GitHub account and Codespaces are optional.

## Work locally

Clone the repository, or use GitHub's **Download ZIP** option and extract it. Install a Java 21 JDK, then run:

```bash
./gradlew test
./gradlew run
```

On Windows, use `gradlew.bat test` and `gradlew.bat run`.

## Files to complete

- `src/main/java/edu/wne/cs366/BinomialCoefficients.java` — implement the three required methods and complete the experiment in `main`.
- `REPORT.md` — record timing data, efficiency and accuracy observations, sources, and the required AI-use disclosure. Export the completed report to `REPORT.pdf` for submission.

Do not change the package, class name, required method signatures, Gradle configuration, or tests. The supplied tests cover representative behavior but are not a complete specification.

## Download your work from Codespaces

You do not need to commit or push your work. Open a terminal at the project root and run:

```bash
zip -r submission.zip . -x 'submission.zip' '.git/*' '.gradle/*' 'build/*'
```

Locate `submission.zip` in the Explorer, right-click it, and select **Download...**. Open the downloaded ZIP and confirm that it contains your completed source and report files, then submit it through Kodiak.
