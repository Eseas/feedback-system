# Feedback System project
## Starting up project
0. In pgAdmin change password of user `postgres` to `testpass`  
1. Open pgAdmin 4
2. Create database `feedbackdb`
3. Open Feedback System project tree in IntelliJ
4. Find `feedback_schema.sql` under `src/main/db`
5. Right-click `Run 'feedback_schema.sql'...`
4. Find `sample_data.sql` under `src/main/db`
5. Right-click `Run 'sample_data.sql'...`

## How to resolve conflicts
1. Open Intellij with our project.
2. VCS -> Git -> Branches
3. Select a branch you want to resolve conflicts with master.
4. Click "Checkout as a new local branch"
5. VCS -> Git -> Pull...
6. Pull master
7. Resolve all conflics:
left part is your local code, middle is result, right is code in master.  
8. Done.

## Coding standard
1. List naming: we use plural, ex.: apples, not appleList.









## .md coding samples below

## Application Server configuration

### TomEE (Apache)
Short version:
1. Download WebProfile, ZIP from: [http://tomee.apache.org/downloads.html](http://tomee.apache.org/downloads.html)
2. Unzip
4. In IntelliJ IDEA: register "TomEE Server" -> local:
    * Press "Fix", choose "exploded war" as artifact
5. Run the server, project should start successfully.

Long version: [TomEE and IntelliJ IDEA](http://tomee.apache.org/tomee-and-intellij.html)