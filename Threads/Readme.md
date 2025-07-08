Difference between Process and Threads?

Threads are fundamental units of executions. Allows program to performa multiple task concurrently. 

They enable developers to create responsive applications, utilize multi-core processors efficiently, and improve overall application performance. 

Cores = Processors. 4 cores means 4 threads can run at a specific point of time. 

Processes vs Threads: Concurrent Execution. 
Both are fundamental concepts but they have distinct characterstics and use cases. 

| Feature           | Process                                      | Thread                                      |
|-------------------|----------------------------------------------|---------------------------------------------|
| Definition        | Independent program in execution              | Smallest unit of execution within a process |
| Memory            | Separate memory space                         | Shares memory with other threads            |
| Communication     | Inter-process communication (slow, complex)   | Inter-thread communication (fast, simple)   |
| Resource Allocation|Fully independent resource allocation         | Share resources with other thread in same process |
| Overhead          | High (more resources required)                | Low (less resources required)               |
| Crash Impact      | Crash affects only the process                | Crash can affect all threads in process     |
| Creation Time     | Slower                                        | Faster                                      |
| Example           | Running two different applications            | Multiple tasks in a single application      |


