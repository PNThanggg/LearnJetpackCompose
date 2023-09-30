package com.pnt.todoapp.data

import com.pnt.todoapp.data.source.local.LocalTask
import com.pnt.todoapp.data.source.network.NetworkTask
import com.pnt.todoapp.data.source.network.TaskStatus

/**
 * Data model mapping extension functions. There are three model types:
 *
 * - com.pnt.todoapp.data.Task: External model exposed to other layers in the architecture.
 * Obtained using `com.pnt.todoapp.data.toExternal`.
 *
 * - com.pnt.todoapp.data.source.network.NetworkTask: Internal model used to represent a task from the network. Obtained using
 * `com.pnt.todoapp.data.toNetwork`.
 *
 * - com.pnt.todoapp.data.source.local.LocalTask: Internal model used to represent a task stored locally in a database. Obtained
 * using `com.pnt.todoapp.data.toLocal`.
 *
 */

// External to local
fun Task.toLocal() = LocalTask(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

fun List<Task>.toLocal() = map(Task::toLocal)

// Local to External
fun LocalTask.toExternal() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

// Note: JvmName is used to provide a unique name for each extension function with the same name.
// Without this, type erasure will cause compiler errors because these methods will have the same
// signature on the JVM.
@JvmName("localToExternal")
fun List<LocalTask>.toExternal() = map(LocalTask::toExternal)

// Network to Local
fun NetworkTask.toLocal() = LocalTask(
    id = id,
    title = title,
    description = shortDescription,
    isCompleted = (status == TaskStatus.COMPLETE),
)

@JvmName("networkToLocal")
fun List<NetworkTask>.toLocal() = map(NetworkTask::toLocal)

// Local to Network
fun LocalTask.toNetwork() = NetworkTask(
    id = id,
    title = title,
    shortDescription = description,
    status = if (isCompleted) { TaskStatus.COMPLETE } else { TaskStatus.ACTIVE }
)

fun List<LocalTask>.toNetwork() = map(LocalTask::toNetwork)

// External to Network
fun Task.toNetwork() = toLocal().toNetwork()

@JvmName("externalToNetwork")
fun List<Task>.toNetwork() = map(Task::toNetwork)

// Network to External
fun NetworkTask.toExternal() = toLocal().toExternal()

@JvmName("networkToExternal")
fun List<NetworkTask>.toExternal() = map(NetworkTask::toExternal)
