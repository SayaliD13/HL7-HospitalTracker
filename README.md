# HL7 QuickScan - Hospital Way-Finding & Patient Tracker

A simple web application that lets hospital visitors search for a patient by name and get their current room location and walking directions. It also includes a staff panel that simulates how a real hospital ADT (Admission, Discharge, Transfer) system pushes live HL7 location updates.

## Why this project

Most patient locator projects stop at a simple search-and-display screen. This one goes a step further by simulating how hospitals actually track and move patients using HL7 messages, since real hospital software never updates a visitor screen manually - it reacts to messages fired by the ADT system. This project mocks that exact workflow: a "Simulate ADT Shift" action fires a new HL7 message, updates the patient record, logs the change, and re-syncs the visitor's screen live.

## Tech Stack

- Frontend: HTML, Tailwind CSS (via CDN), Vanilla JavaScript
- Backend: Core Java Servlets (JDBC, no Spring/Hibernate)
- Database: MySQL

## Features

- Visitor search with duplicate-name handling (shows Name, Age, City to pick the right patient)
- Mock HL7 PV1 segment parsing (Ward / Room / Bed extracted from a string like `PV1||I|General Ward^Room 04^Bed 12`)
- Plain-text indoor navigation route for each ward, available in English and Hindi
- Staff-only panel (login required) to:
  - Add or delete patient records
  - Simulate a live ADT room shift, which updates the patient's location instantly (no page reload) and logs the change in an audit table
- Visitor screen re-syncs live and shows an "HL7 RE-SYNCED" badge when a shift happens while it is open

## Key learnings

- How HL7 messages carry patient location data, and how to parse a segment into usable fields
- How real hospitals separate a visitor-facing view from a staff/admin-facing view
- Session-based access control in plain servlets, without a security framework
- Building an async (AJAX-style) update flow with plain JavaScript fetch calls, so the screen updates without a full page reload
- Keeping an audit trail (a simple log table) so every change to a patient's location has a history

## Project Structure

src/            Java servlet classes
web/            index.html, CSS, JS, WEB-INF (web.xml, MySQL driver jar)
database.sql    Table creation + sample patient data

## Setup

1. Run `database.sql` in MySQL to create the `hospital_db` database and sample patients.
2. Open the project in an IDE that supports Java Web Applications (e.g. NetBeans) with Apache Tomcat configured.
3. Update the database username/password in `DBConnect.java` if needed.
4. Run the project - it opens `index.html` in the browser via Tomcat.

## Staff Login (demo credentials)

- Name: `Admin`
- ID: `STAFF001`

## Future scope

- Connect to a real ADT/HL7 feed instead of a simulated one
- Support multiple hospitals/branches in the same system
- Replace the hardcoded staff login with a proper authentication system
