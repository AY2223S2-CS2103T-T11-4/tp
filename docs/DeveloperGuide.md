---
layout: page title: Developer Guide
---

* Table of Contents {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
  original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in
the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML
Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit
diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes
called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It
is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`
, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**
API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is
   executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API
call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**
API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/NricModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterNricModelClassDiagram.png" width="450" />

</div>

### Storage component

**
API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in json format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Backup/Load feature

The backup feature is facilitated by BackupCommand.

<img src="images/BackupSequenceDiagram.png" />

### Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`
, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

### Adding Nric as identifier

#### Proposed Implementation

The proposed `Nric` field is done similar to the implementation of the `Name` field.

Previously, name was used as the unique identifier for a `Person` object, where we check for equality between
two `Person` objects by name matching. As we acknowledge that in a clinical/hospital system, several patients may have
the same name, `Nric` was identified as a better unique identification choice.

The following additional constraints will be applied:

1. `Nric` will be mandatory field when adding a new `Person`.
2. `Nric` has to be in the following format: `@xxxxxxx#`
    1. `@` has to be one of the following: `S`, `T`, `F`, `G`, or `M`
    2. `xxxxxxx` is a 7-digit serial number, each `x` can be any number `0-9`
    3. `#` can be any capital alphabet `A-Z`, and the field cannot be blank.
3. `Nric` must be unique, the system will not allow the addition of a new person otherwise

Given below is an updated `Model` component diagram.

<img src="images/NricModelClassDiagram.png" width="450" />

#### Design considerations:

**Aspect: Mutability of `Nric` field:**

* **Alternative 1 (current choice):** `Nric` is mutable.
    * Pros: Easy to make corrections if entered wrongly, no need to type the entire `add` command again
    * Cons: `Nric` never changes for a person, it may not make sense to make it mutable.

* **Alternative 2:** `Nric` is immutable.
    * Pros: Will ensure no tampering of identifier for a `Person` object.
    * Cons: If `Nric` is wrongly entered, user will have to re-type the entire `add` command.
        * This can have heavier consequences if much more data is added before the mistake is noticed.

### Delete patient record by NRIC feature

#### Implementation

The implemented delete mechanism is facilitated by `DeleteCommandParser`. It extends `AddressBookParser` and implements
the following operations:

* `DeleteCommandParser#parse()` — Parses user input into `ArrayList<NRIC>` and creates a `DeleteCommand` object

These operations are exposed in the Model interface as methods with the same name e.g.
`Model#deletePerson()`.

Given below is an example usage scenario and how the delete command works at each step

Step 1. The clinical/hospital administrator has been informed of a patient's death and the patient's NRIC, S1234567A

Step 2. The administrator executes `delete i/S1234567A`. The `DeleteCommand` is executed and for each `NRIC`
in the `ArrayList<NRIC>`, `Model#findPersonByNric()` is called and followed by a call to `Model#deletePerson()`
which deletes the record in the system with the specified `NRIC`.

The following sequence diagram shows how the delete command works:

#### Design considerations:

**Aspect: Deletion criteria**

* **Alternative 1 (current choice):** Deletion by `NRIC`.
    * Pros: Very efficient as program will search for the record with specified `NRIC` and delete it.
    * Cons: Might be less convenient for clinical administrator to type out `NRIC` as compared to INDEX especially for
      the top few records displayed.

* **Alternative 2:** Deletion by INDEX.
    * Pros: More convenient for clinical administrator to type out INDEX for the top few displayed records.
    * Cons: If the record we are searching for does not appear in the top few records, we would have to execute a find
      command and then get corresponding INDEX to carry out deletion.

### Light / Dark Theme

#### Proposed Implementation

The theme is facilitated by `Theme`. It contains two modes which are light mode and dark mode. The light mode is closely
related to `LightModeCommand` and `LightTheme.css`. The dark mode is associated to `DarkModeCommand` and `DarkTheme.css`
. Moreover, two attributes (`showLight` and `showDark`) of `CommandResult` indicate what the current theme is.

Given below is an example usage scenario and how the light/dark mechanism behaves at each step.

Step 1. The user launches the application and the current theme is Dark.

Step 2. The user executes `light` command and the theme changed to light mode.

Step 3. The user clicks the `Theme` button above and select the `Dark` choice. The theme go back to the dark mode again.

The following activity diagram summarizes what happens when a user executes these commands or clicks the `Theme` button:

#### Design considerations:

**Aspect: How light & redo executes:**

* **Alternative 1 (current choice):** CSS file for Dark and Light separately.
    * Pros: Easy to implement.
    * Cons: More resource space and necessity to switch between two file paths.

* **Alternative 2:** One CSS file containing the information of two modes.
    * Pros: Less resource space and no need to change the file path.
    * Cons: Not easy to implement and require more FXML changes.

### \[Proposed\] Find command

Proposed Implementation

_{Explain here how the find feature will be implemented}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: HospiSearch is a comprehensive hospital records management system designed to streamline
administrative tasks and improve patient care. With our app, you can easily store, retrieve, and manage patient records,
appointment schedules, and billing information all in one place

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see the user guide                      | know about all functions            |
| `* * *`  | new user                                   | access a help menu                  |  know about all commands            |
| `* * *`  | admin                                      | add patients’ records                  | keep track of their information     |
| `* * *`  | admin                                      | edit patients’ records                | update their information        |
| `* * *`  | admin                                      | delete patients’ records          |                			    |
| `* *`    | admin                              | import data files of different formats  |						    |
| `* *`    | admin                              | list all patients                       | have an overview                |
| `* *`    | admin                              | search for a patient record          | find the needed information quickly |
| `*`      | admin                              | clear data                              | start the database from scratch     |
| `*`      | admin                              | save data                               | resume the same state next time     |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `HospiSearch` and the **Actor** is the `user`, unless specified
otherwise)

**Use case: UC1 - Add patient to system**

**MSS**

1. Administrator types add command together with the patient details (NRIC, age, gender, medicine usage, health
   conditions).
2. HS adds the patient to the system. Use case ends.

**Extensions**

* 1a. HS detects an error in the entered patient details or missing patient details.

  1a1. HS requests for the correct data. 1a2. Administrator enters the new data. Steps 1a1-1a2 are repeated until the
  data entered are correct. Use case resumes at step 2.

**Use case: UC2 - Edit patients' details**

**MSS**

1. Administrator requests to update a patients' details and enters the updated information.
2. HS updates the patient's record in the system. Use case ends.

**Extensions**

* 1a. HS detects an error in the updated information.

  1a1. HS requests for the correct data. 1a2. Administrator enters the new data. Steps 1a1-1a2 are repeated until the
  data entered are correct. Use case resumes at step 2.

**Use case: UC3 - Help Administrator obtain information on HospiSearch**

**MSS**

1. Administrator is new to the system and requests for help.
2. HS gives a list of all possible commands together with brief descriptions to guide the user. Use case ends.

**Use case: UC4 - Clear all data**

**MSS**

1. Administrator requests to clear all data in the system.
2. HS clears all the data in the system. Use case ends.

**Use case: UC5 - Search for patients by address**

**MSS**

1. Administrator wants to search for patient by his address
2. HS provides a list of all people staying in the given address

**Extensions**

* 1a. No such patient has the specified address. Use case ends.

**Use case: UC6 - Search for patients by medicine**

**MSS**

1. Administrator requests for a list of patients using a certain type of medicine as he wants to stock up on medicine.
2. HS provides a list of patients using the specified type of medicine. Use case ends.

**Extensions**

* 1a. There are no patients using the specified type of medicine in HS system. Use case ends.

**Use case: UC7 - Get patient by NRIC**

**MSS**

1. Administrator wants to get patient details’ based on the patient’s NRIC.
2. HS provides the details of the patient with the specified NRIC. Use case ends.

**Extensions**

* 1a. There is no such patient with specified NRIC in the HS system. Use case ends.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. Should be able to produce find results in less than 3 seconds for a database of less than 10000 user information.
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
