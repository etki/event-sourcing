# Terms

## Event and transition

This project makes difference between event and transition; while this 
is not strictly necessary, this places large part of burden onto 
library's shoulders.

**Transition** is simply state transition for entity, e.g. "set product 
count to 13", and transitions often don't require previous entity state
or it's modification index.

**Event** is a transition that has taken place at a certain time, with
a certain modification index on an entity with certain ID. While 
transition is simply the action applied on the entity, event embraces 
that action and all additional information it can.

I don't think that end user has to tinker with all automatable staff,
so this project takes responsibility for event metadata handling 
wherever it can, leaving end user to deal with particular transitions 
only.