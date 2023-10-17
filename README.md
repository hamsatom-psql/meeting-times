# meeting-times
## A small program that can list available times for a meeting for several participants
The program should take the following inputs:
- Ids of the calendars to be searched,
- The length (duration in minutes) of the meeting,
- A period within to find availability (ISO8601 time interval, `2019-03-01T13:00:00Z/2019-05-11T15:30:00Z`)
- **Optionally** the 4th argument to the service so that it can search the
availability of a specific time slot type.

## Running the solution

1. build by executing in project root

```shell
./gradlew build
```

2. run Java application with main class [MainClass](src/main/java/org/MainClass.java)

You can change meeting parameters at [MainClass:30](src/main/java/org/MainClass.java)
and `findAvailableTime()` (both with/without type) is located
at [DefaultMeetingService](src/main/java/org/service/DefaultMeetingService.java). 
