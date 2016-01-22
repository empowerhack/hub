# EmpowerHack Hub

## ToDo

* growl notifications (future would have filters & able to watch/unwatch items)
    - alert calendar updates (save)
    - alert project events (save)

---

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
    * documents url - title, url (similar to member.socials)
    * simple search
    * activity
    * members on the project (many-to-many)
    * calendar - read all relevant calendar items

---

* Calendar
    * planned attendees (required/optional)
    * who actually attended
    * notes from meeting
    * private / public?
    * List
        * Historic - in date order from today
        * Future - in date order from today
        * required/optional attendees

---

* Member improvements
    * Visibility section: public / private (if member of organisation, change this too on GitHub)
    * Get user's GitHub profile - avatar url and save (have force update button)
* deploy - DB separate vm?

---

* Audit everything
    * all tables
    * activity page for member / project / calendar etc
    * user activity
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
