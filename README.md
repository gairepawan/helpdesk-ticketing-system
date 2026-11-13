# IT Help Desk Ticketing System (Java)

Java 21 portfolio project inspired by enterprise ServiceNow-style incident management. Independent project, not affiliated with ServiceNow.

**Status: Phase 1 — Command-line demo.** This is not yet a Spring Boot web app. Database persistence, authentication, browser UI, and deployment are planned, not implemented.

## Implemented features

- Create incidents with automatically generated IDs, such as `INC0001001`.
- Priorities: `CRITICAL`, `HIGH`, `MEDIUM`, `LOW`.
- Statuses: `NEW`, `IN_PROGRESS`, `ON_HOLD`, `RESOLVED`, `CLOSED`.
- Validate ticket descriptions; find tickets by ID; change ticket status.
- Display tickets ordered by priority, creation time, and ID.
- Four JUnit 5 tests and a GitHub Actions CI workflow.

Tickets are stored in memory and **disappear when the application exits**. Status transitions are not yet validated. Do not use actual employee or customer records.

## Prerequisites

Install **JDK 21**, **Apache Maven 3.9+**, and **Git**. IntelliJ IDEA or VS Code is optional.

```bash
java -version
mvn -version
git --version
```

## Clone and run

```bash
git clone https://github.com/gairepawan/helpdesk-ticketing-system.git
cd helpdesk-ticketing-system
mvn clean verify
java -cp target/classes com.helpdesk.App
```

Or select **Code → Download ZIP** on GitHub, extract it, and run the commands inside the directory containing `pom.xml`.

Expected console output:

```text
Tickets ordered by priority:
INC0001003 | CRITICAL | NEW | Company network is down
INC0001001 | HIGH | NEW | Cannot connect to office Wi-Fi
INC0001002 | LOW | IN_PROGRESS | Printer jam on floor 2
```

Run just the tests with `mvn test`.

## Project structure

```text
helpdesk-ticketing-system/
├── .github/workflows/ci.yml
├── .gitignore
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/helpdesk/
    │   ├── App.java
    │   ├── Ticket.java
    │   └── TicketService.java
    └── test/java/com/helpdesk/
        └── TicketServiceTest.java
```

- **App.java:** program entry point and demo.
- **Ticket.java:** incident model including ID, description, priority, creation time, and status.
- **TicketService.java:** creates incidents, looks them up, changes status, counts incidents, and sorts them.
- **TicketServiceTest.java:** verifies basic behavior and error cases.
- **pom.xml:** Maven build definition for Java 21 and JUnit 5.

## Java example

```java
TicketService service = new TicketService();
Ticket ticket = service.create("VPN is disconnected", Ticket.Priority.HIGH);
service.updateStatus(ticket.getId(), Ticket.Status.IN_PROGRESS);
System.out.println(service.find(ticket.getId()));
service.allByPriority().forEach(System.out::println);
```

## Data structures and algorithms (DSA)

| Operation | Technique | Average time |
| --- | --- | --- |
| Create, find, update ticket | `HashMap<String, Ticket>` | O(1) |
| Count tickets | `HashMap.size()` | O(1) |
| List tickets by priority | `ArrayList`, `Comparator`, sorting | O(n log n) |

`HashMap` performance is **average case**, not guaranteed worst case. The sorting operation also requires O(n) additional space for the copied list. The enum's declaration order determines the priorities.

## Automated testing and CI/CD

Four JUnit 5 tests cover: unique incident IDs and initial NEW status; Critical-first ordering; status updates and missing-ticket errors; and rejecting blank descriptions.

GitHub Actions in `.github/workflows/ci.yml` runs on pushes to `main` and pull requests targeting `main`. It uses Java 21 and executes:

```bash
mvn --batch-mode verify
```

Check **GitHub → Actions → Java CI** for results. The workflow implements **continuous integration**, not deployment. Continuous delivery and deployment will come later.

## Contributing and Git workflow

```bash
git checkout -b feature/ticket-search
# Make changes and write tests
mvn clean verify
git add .
git commit -m "feat: implement ticket search"
git push -u origin feature/ticket-search
```

Open a pull request, review the CI results, and merge into `main` after passing checks.

## Roadmap

1. Ticket search, filtering, lifecycle transition rules, and more tests.
2. Spring Boot REST API with controller/service/repository layers.
3. MySQL or PostgreSQL persistence with migrations.
4. Login, role-based access, technician assignment, work notes, audit history.
5. Browser-based dashboard, integration testing, monitoring, and deployment pipeline.
6. DSA extensions: priority queues, database indexing, ticket assignment strategies.

## Troubleshooting

- `java` not found: install JDK 21 and configure `JAVA_HOME` and `PATH`.
- `mvn` not found: install Maven and restart the terminal.
- Wrong JDK: check that `mvn -version` reports Java 21.
- Main class not found: run `mvn clean verify` before starting the demo.
- CI failure: inspect the failing job in **GitHub → Actions**, and reproduce with `mvn clean verify` locally.

## Security and license

Never commit passwords, access tokens, `.env` files, or real support tickets. Authentication and other controls are not yet implemented. No license has been selected, so default copyright restrictions apply.
