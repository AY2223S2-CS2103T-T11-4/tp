---
layout: page
title: User Guide
---

HospiSearch is a **desktop app for managing hospital patients' particulars, optimized for use via a Command Line Interface** 
(CLI) while still having the benefits of a **Graphical User Interface (GUI)**. If you can type fast, HospiSearch can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents: coming soon

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `HospiSearch.jar` from [here](https://github.com/AY2223S2-CS2103T-T11-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your HospiSearch.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hospisearch.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
5. Type in a command in the command box to execute it. Some commands to try:
   1. `help` opens up the help menu
6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

### Notes:
- Words in `UPPER_CASE` are the parameters to be supplied by the user. 
e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.
e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…` after them can be used multiple times including zero times.
e.g. [t/TAG]…​ can be used as   (i.e. 0 times), t/friend, t/friend t/family etc.
- Parameters can be in any order.
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.




  
### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`


### Adding a person: `add`

Adds a person to the patient records.

Format: `add n/NAME i/NRIC p/PHONE_NUMBER e/EMAIL a/ADDRESS [d/DIAGNOSIS] [t/TAG]…`


**Tip**: A person can have any number of tags (including 0)


Examples:
* `add i/T0012345A n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/Diabetic`
* `add i/T0012345B n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison p/1234567 t/Dyslexic d/Osteoporotic`



### Editing a person: `edit`

Edits an existing person in the patient record.

Format: `edit i/NRIC [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DIAGNOSIS] [t/TAG]…​`

* You can remove all the person’s tags by typing t/ without specifying any tags after it.

Examples:
* `edit i/T0012345A p/91234567 e/johndoe@example.com` edit the phone number and email address of the patient with NRIC T0012345A to be 91234567 and johndoe@example.com respectively


### Deleting a person: `delete`

Deletes the specified person from the address book.

Format: `delete i/NRIC`

Examples:
* `delete i/T0012345A` delete the patient with NRIC T0012345A from patient records system

### Find persons by nric, name, address or tags: `find`


Find persons according to a particular attribute stated followed by the change. 

Eg. name(`n/`), address(`a/`), nric(`i/`), tag(`t/`)

Format: `find attribute/keyword [MORE_KEYWORDS]`


* The search will only be carried out for the given attribute
* Only one attribute can be searched at one time
* The search is case-insensitive. e.g panadol will match pANAdol
* The order of the keywords does matters. e.g. "panadol" will match "medicine panadol"
* Can input multiple keywords for a given attribute and all matching persons will be returned


Examples:
* `find n/john` returns `John Lim` and `John Doe` who both contain the name `John` in their names
* `find a/serangoon` returns `Alice Tan` and `John Doe` who have an address located in `Serangoon`
* `find i/S0078957G` returns `Alice Tan` who has an NRIC of `S0078957G`
* `find a/ang mo kio serangoon` returns 'Alice Tan', 'John Doe', 'John Lim' who all stay either in `ang mo kio` or  `serangoon`
* `find t/Diabetic` returns all persons with the tag `Diabetic`
* `find t/Diabetic Osteoporotic` returns all persons with the tag `Diabetic` or `Osteoporotic` or both.

### Clearing all data: `clear`
Purges all data from the database

Format: `clear`

### Backup data: `backup`
Backs up the data to a specified slot represented by an index

Format: `backup INDEX_NO`

Examples:
* `backup 3` backups the data to the 3rd slot

Tip: INDEX_NO can only be an integer between 1 and 10

### Load data: `load`
Loads the data from a specified slot represented by an index

Format: `load INDEX_NO`

Example: 
* `load 3` loads the data from the 3rd slot

### Help menu: `help`
Lists out all the commands available, along with a brief description

Format: `help`

### Listing all persons : `list`

Shows a list of all people in the address book.

Format: `list`

### Switch to light mode: `light`
Switch to light mode.
Format: `light`

### Switch to dark mode: `dark`
Switch to dark mode.
Format: `dark`

### Undoing previous command: `undo`
Reverts the address book to the state before the previous command was executed.
Format: `undo`

### Redoing previous undo: `redo`
Reverts the address book to the state before the previous undo was executed.
Format: `redo`



--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HospiSearch home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action        | Format, Examples                                                                                                                                                                                |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**       | `add n/NAME i/NRIC p/PHONE_NUMBER e/EMAIL a/ADDRESS [d/diagnosis] [t/TAG]…​` <br/> e.g. add n/John Doe i/T0012345A p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/depression |                                                                                                                                                      |
| **Clear all** | `clear`                                                                                                                                                                                         |
| **Delete**    | `delete i/NRIC` <br/> e.g. delete i/T0012345A                                                                                                                                                   |
| **Edit**      | `edit i/NRIC [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DIAGNOSIS] [t/TAG]…​` <br/> e.g. edit i/T0012345A p/91234567 e/johndoe@example.com                                                     |
| **Find**      | `find attribute/KEYWORD [MORE_KEYWORDS]` <br/> e.g. find a/Alex  <br/> e.g. find t/diabetic                                                                                                     |
| **Backup**    | `backup INDEX_NO` <br/> e.g. backup 3                                                                                                                                                           |
| **Save**      | `save INDEX_NO` <br/> e.g. save 3                                                                                                                                                               |
| **List**      | `list`                                                                                                                                                                                          |
| **Help**      | `help`                                                                                                                                                                                          |
| **Light**     | `light`                                                                                                                                                                                         |
| **Dark**      | `dark`                                                                                                                                                                                          |
| **Undo**      | `undo`                                                                                                                                                                                          |
| **Redo**      | `redo`                                                                                                                                                                                          |
