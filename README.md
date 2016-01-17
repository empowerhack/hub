# EmpowerHack Hub

## ToDo

* Update profile
    * availability to be a slider?

* Members list
    * list results
    * From DB - Everyone who has logged in
    * Core member badge - Members list from GitHub (in-memory cache on timer)
    * private / public - logged in / guest
    * simple search

---

* CRUD project
    * github repo url
    * website url
    * simple search
    * members on the project (many-to-many)
    * calendar - read all relevant calendar items
    * activity
    * last updated

---

* Calendar
    * Entity
        * project
        * creator (member)
        * date (date picker)
        * time
        * duration
        * title
        * description - details  / agenda etc
        * location
        * planned attendees (required/optional)
        * who actually attended
        * notes from meeting
        * private / public?
    * List
        * in date order from today
        * required/optional attendees
        * date / time / duration / title / description

---

* Member improvements
    * Visibility section: public / private (if member of organisation, change this too on GitHub)
    * Get user's GitHub profile - avatar url and save (have force update button)
* deploy - DB separate vm?

---

* Audit everything
    * all tables
    * activity page
    * display for item

---

* Notifications (tab section under accounts)
    * Websockets & Growl
        * eg. new user / updated user / new project / updated project / new calendar / updated calender
    * Email? (disable for now)
    * User configure preferences - new user, updated user, etc...

---

* Member list
    * facets - save / sync to ES
    * search with facets

---

* Project list
    * facets - save / sync to ES
    * search with facets