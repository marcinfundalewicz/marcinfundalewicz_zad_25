package pl.javastart.jjdind84_marcinfundalewicz_zad_25;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        Map<Priority, List<Task>> collect = taskRepository.findAllByDoneIsFalse()
                .stream()
                .collect(Collectors.groupingBy(Task::getPriority));

        model.addAttribute("tasksHigh", collect.getOrDefault(Priority.HIGH, Collections.emptyList()));
        model.addAttribute("tasksMedium", collect.getOrDefault(Priority.MEDIUM, Collections.emptyList()));
        model.addAttribute("tasksLow", collect.getOrDefault(Priority.LOW, Collections.emptyList()));

        return "home";
    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("task", new Task());
        return "addTask";
    }

    @GetMapping("/archive")
    public String showDoneTasks(Model model) {
        List<Task> tasksDone = taskRepository.findAllByDoneIsTrueOrderByCompletionDate();
        model.addAttribute("tasksDone", tasksDone);

        return "archive";
    }

    @GetMapping("/task/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Task> taskById = taskRepository.findById(id);

        if (taskById.isPresent()) {
            Task task = taskById.get();
            model.addAttribute("task", task);
            return "editTask";
        }
        return "redirect:/";
    }

    @PostMapping("/task/edit")
    public String editTask(Task task) {

        taskRepository.save(task);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addTask(Task task) {

        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/task/info/{id}")
    public String showtaskInfoForm(@PathVariable Long id, Model model) {
        Optional<Task> taskById = taskRepository.findById(id);

        if (taskById.isPresent()) {
            Task task = taskById.get();
            model.addAttribute("task", task);
            return "taskInfoForm";
        }

        return "redirect:/";
    }

    @PostMapping("/task/save/{id}")
    public String saveTask(@RequestParam("taskId") Long id, @ModelAttribute Task task) {
        Optional<Task> taskById = taskRepository.findById(id);

        if (taskById.isPresent()) {
            Task taskToSave = taskById.get();
            taskToSave.setDone(true);
            taskToSave.setDuration(task.getDuration());
            taskToSave.setCompletionDate(task.getCompletionDate());
            taskRepository.save(taskToSave);
        }
        return "redirect:/";
    }
}
