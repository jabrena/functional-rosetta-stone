```
[25.528s][info   ][gc,stats] 
[25.528s][info   ][gc,stats] GC STATISTICS:
[25.528s][info   ][gc,stats]   "(G)" (gross) pauses include VM time: time to notify and block threads, do the pre-
[25.528s][info   ][gc,stats]         and post-safepoint housekeeping. Use -XX:+PrintSafepointStatistics to dissect.
[25.528s][info   ][gc,stats]   "(N)" (net) pauses are the times spent in the actual GC code.
[25.528s][info   ][gc,stats]   "a" is average time for each phase, look at levels to see if average makes sense.
[25.528s][info   ][gc,stats]   "lvls" are quantiles: 0% (minimum), 25%, 50% (median), 75%, 100% (maximum).
[25.528s][info   ][gc,stats] 
[25.528s][info   ][gc,stats] Total Pauses (G)            =     1.16 s (a =    12902 us) (n =    90) (lvls, us =      408,     9199,    10742,    16406,    46702)
[25.528s][info   ][gc,stats] Total Pauses (N)            =     0.49 s (a =     5425 us) (n =    90) (lvls, us =      100,     2031,     4844,     6387,    36279)
[25.528s][info   ][gc,stats] Pause Init Mark (G)         =     0.27 s (a =    10477 us) (n =    26) (lvls, us =      406,     9355,    10156,    11719,    22380)
[25.528s][info   ][gc,stats] Pause Init Mark (N)         =     0.14 s (a =     5418 us) (n =    26) (lvls, us =      283,     4844,     5430,     5996,    10043)
[25.528s][info   ][gc,stats]   Make Parsable             =     0.02 s (a =      791 us) (n =    26) (lvls, us =        6,      635,      727,      885,     2434)
[25.528s][info   ][gc,stats]   Clear Liveness            =     0.00 s (a =       90 us) (n =    26) (lvls, us =       33,       63,       83,       99,      179)
[25.528s][info   ][gc,stats]   Scan Roots                =     0.11 s (a =     4116 us) (n =    26) (lvls, us =      230,     3574,     4121,     4531,     6792)
[25.528s][info   ][gc,stats]     S: Thread Roots         =     0.08 s (a =     3210 us) (n =    26) (lvls, us =       53,     3066,     3320,     3633,     4947)
[25.528s][info   ][gc,stats]     S: String Table Roots   =     0.00 s (a =       18 us) (n =    26) (lvls, us =        0,        0,        0,        0,      136)
[25.528s][info   ][gc,stats]     S: Universe Roots       =     0.00 s (a =        2 us) (n =    26) (lvls, us =        1,        2,        2,        2,       14)
[25.528s][info   ][gc,stats]     S: JNI Roots            =     0.00 s (a =       11 us) (n =    26) (lvls, us =        6,        7,        7,        8,       49)
[25.528s][info   ][gc,stats]     S: JNI Weak Roots       =     0.00 s (a =       15 us) (n =    26) (lvls, us =        0,        0,        0,        0,      171)
[25.528s][info   ][gc,stats]     S: Synchronizer Roots   =     0.00 s (a =        0 us) (n =    26) (lvls, us =        0,        0,        0,        0,        0)
[25.528s][info   ][gc,stats]     S: Management Roots     =     0.00 s (a =        3 us) (n =    26) (lvls, us =        1,        2,        2,        2,       12)
[25.528s][info   ][gc,stats]     S: System Dict Roots    =     0.00 s (a =       17 us) (n =    26) (lvls, us =       11,       12,       13,       14,       83)
[25.528s][info   ][gc,stats]     S: CLDG Roots           =     0.01 s (a =      215 us) (n =    26) (lvls, us =       46,       65,       71,       81,     1694)
[25.528s][info   ][gc,stats]     S: JVMTI Roots          =     0.00 s (a =       76 us) (n =    26) (lvls, us =        1,       59,       77,       97,      133)
[25.529s][info   ][gc,stats]   Resize TLABs              =     0.01 s (a =      282 us) (n =    26) (lvls, us =        2,      244,      273,      322,      649)
[25.529s][info   ][gc,stats] Pause Final Mark (G)        =     0.39 s (a =    15162 us) (n =    26) (lvls, us =     2363,    10742,    13477,    16406,    46699)
[25.529s][info   ][gc,stats] Pause Final Mark (N)        =     0.25 s (a =     9545 us) (n =    26) (lvls, us =     2227,     4902,     7949,     9961,    36277)
[25.529s][info   ][gc,stats]   Finish Queues             =     0.04 s (a =     1634 us) (n =    26) (lvls, us =       69,      395,      451,      537,    11281)
[25.529s][info   ][gc,stats]   Weak References           =     0.02 s (a =      828 us) (n =    23) (lvls, us =       48,       77,      109,      289,    14918)
[25.529s][info   ][gc,stats]     Process                 =     0.02 s (a =      816 us) (n =    23) (lvls, us =       38,       67,      100,      277,    14901)
[25.529s][info   ][gc,stats]   System Purge              =     0.08 s (a =     3505 us) (n =    22) (lvls, us =     1152,     2363,     2637,     3281,    14855)
[25.529s][info   ][gc,stats]     Unload Classes          =     0.01 s (a =      421 us) (n =    22) (lvls, us =       25,       53,       55,       59,     5566)
[25.529s][info   ][gc,stats]     Parallel Cleanup        =     0.07 s (a =     2984 us) (n =    22) (lvls, us =     1113,     2305,     2578,     3223,     7226)
[25.529s][info   ][gc,stats]     CLDG                    =     0.00 s (a =       95 us) (n =    22) (lvls, us =        0,        0,        0,        1,     2047)
[25.529s][info   ][gc,stats]   Complete Liveness         =     0.00 s (a =       71 us) (n =    26) (lvls, us =       48,       50,       54,       77,      160)
[25.529s][info   ][gc,stats]   Prepare Evacuation        =     0.02 s (a =      903 us) (n =    26) (lvls, us =      213,      725,      820,      996,     2559)
[25.529s][info   ][gc,stats]   Initial Evacuation        =     0.08 s (a =     4165 us) (n =    19) (lvls, us =      734,     3301,     4238,     4648,     7755)
[25.529s][info   ][gc,stats]     E: Thread Roots         =     0.06 s (a =     3314 us) (n =    19) (lvls, us =      152,     2480,     2988,     3926,     6259)
[25.529s][info   ][gc,stats]     E: Code Cache Roots     =     0.00 s (a =      135 us) (n =    19) (lvls, us =       48,       72,      104,      125,      525)
[25.529s][info   ][gc,stats]     E: JVMTI Roots          =     0.00 s (a =        1 us) (n =    19) (lvls, us =        0,        1,        1,        1,        2)
[25.529s][info   ][gc,stats] Pause Init  Update Refs (G) =     0.32 s (a =    16844 us) (n =    19) (lvls, us =     2246,     7695,    18359,    21289,    24356)
[25.529s][info   ][gc,stats] Pause Init  Update Refs (N) =     0.02 s (a =     1164 us) (n =    19) (lvls, us =       94,      934,     1113,     1328,     2031)
[25.529s][info   ][gc,stats] Pause Final Update Refs (G) =     0.17 s (a =     9169 us) (n =    19) (lvls, us =      553,     7617,     8574,     9453,    21254)
[25.529s][info   ][gc,stats] Pause Final Update Refs (N) =     0.08 s (a =     4052 us) (n =    19) (lvls, us =      381,     3066,     3535,     4688,     7340)
[25.529s][info   ][gc,stats]   Update Roots              =     0.07 s (a =     3833 us) (n =    19) (lvls, us =      170,     2891,     3340,     4375,     7115)
[25.529s][info   ][gc,stats]     UR: Thread Roots        =     0.05 s (a =     2772 us) (n =    19) (lvls, us =       15,     2402,     2676,     3203,     4030)
[25.529s][info   ][gc,stats]     UR: String Table Roots  =     0.00 s (a =       31 us) (n =    19) (lvls, us =       26,       26,       28,       32,       53)
[25.529s][info   ][gc,stats]     UR: Universe Roots      =     0.00 s (a =        1 us) (n =    19) (lvls, us =        1,        1,        1,        1,        1)
[25.529s][info   ][gc,stats]     UR: JNI Roots           =     0.00 s (a =        2 us) (n =    19) (lvls, us =        1,        1,        2,        2,        5)
[25.529s][info   ][gc,stats]     UR: JNI Weak Roots      =     0.00 s (a =       38 us) (n =    19) (lvls, us =       24,       27,       31,       36,      138)
[25.529s][info   ][gc,stats]     UR: Synchronizer Roots  =     0.00 s (a =        0 us) (n =    19) (lvls, us =        0,        0,        0,        0,        0)
[25.529s][info   ][gc,stats]     UR: Management Roots    =     0.00 s (a =        2 us) (n =    19) (lvls, us =        1,        1,        2,        2,        2)
[25.529s][info   ][gc,stats]     UR: System Dict Roots   =     0.00 s (a =        8 us) (n =    19) (lvls, us =        7,        7,        8,        8,       13)
[25.529s][info   ][gc,stats]     UR: CLDG Roots          =     0.00 s (a =       88 us) (n =    19) (lvls, us =       40,       45,       54,       71,      333)
[25.529s][info   ][gc,stats]     UR: JVMTI Roots         =     0.00 s (a =       80 us) (n =    19) (lvls, us =        2,       54,       72,       95,      161)
[25.530s][info   ][gc,stats]   Recycle                   =     0.00 s (a =      197 us) (n =    19) (lvls, us =      137,      145,      176,      213,      364)
[25.530s][info   ][gc,stats] Concurrent Reset            =     0.04 s (a =     1415 us) (n =    26) (lvls, us =      211,      307,      514,     2402,     4856)
[25.530s][info   ][gc,stats] Concurrent Marking          =     0.40 s (a =    15316 us) (n =    26) (lvls, us =     3320,    11328,    13867,    18164,    33391)
[25.530s][info   ][gc,stats] Concurrent Precleaning      =     0.02 s (a =      872 us) (n =    23) (lvls, us =      199,      664,      852,      961,     1948)
[25.530s][info   ][gc,stats] Concurrent Evacuation       =     0.10 s (a =     5515 us) (n =    19) (lvls, us =      422,     1621,     2207,     5039,    23817)
[25.530s][info   ][gc,stats] Concurrent Update Refs      =     0.18 s (a =     9674 us) (n =    19) (lvls, us =     3848,     5723,     7051,    10547,    24781)
[25.530s][info   ][gc,stats] Concurrent Cleanup          =     0.01 s (a =      215 us) (n =    45) (lvls, us =       53,      148,      180,      227,      662)
[25.530s][info   ][gc,stats] Concurrent Uncommit         =     0.10 s (a =     4707 us) (n =    22) (lvls, us =      113,      219,      760,     4023,    29555)
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] Under allocation pressure, concurrent cycles may cancel, and either continue cycle
[25.530s][info   ][gc,stats] under stop-the-world pause or result in stop-the-world Full GC. Increase heap size,
[25.530s][info   ][gc,stats] tune GC heuristics, set more aggressive pacing delay, or lower allocation rate
[25.530s][info   ][gc,stats] to avoid Degenerated and Full GC cycles.
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats]    26 successful concurrent GCs
[25.530s][info   ][gc,stats]      22 invoked explicitly
[25.530s][info   ][gc,stats]       0 invoked implicitly
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats]     0 Degenerated GCs
[25.530s][info   ][gc,stats]       0 caused by allocation failure
[25.530s][info   ][gc,stats]       0 upgraded to Full GC
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats]     0 Full GCs
[25.530s][info   ][gc,stats]       0 invoked explicitly
[25.530s][info   ][gc,stats]       0 invoked implicitly
[25.530s][info   ][gc,stats]       0 caused by allocation failure
[25.530s][info   ][gc,stats]       0 upgraded from Degenerated GC
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] ALLOCATION PACING:
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] Max pacing delay is set for 10 ms.
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] Higher delay would prevent application outpacing the GC, but it will hide the GC latencies
[25.530s][info   ][gc,stats] from the STW pause times. Pacing affects the individual threads, and so it would also be
[25.530s][info   ][gc,stats] invisible to the usual profiling tools, but would add up to end-to-end application latency.
[25.530s][info   ][gc,stats] Raise max pacing delay with care.
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] Actual pacing delays histogram:
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats]       From -         To         Count         Sum
[25.530s][info   ][gc,stats]                   Total:            0           0 ms
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats] 
[25.530s][info   ][gc,stats]   Allocation tracing is disabled, use -XX:+ShenandoahAllocationTrace to enable.

```