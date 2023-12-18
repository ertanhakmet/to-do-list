// DESIGN
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ToDoListGui extends JFrame {

    DefaultListModel<String> model;
    JList<String> task_list;
    JTextField task_entry;
    JButton add_button, remove_button, load_button, save_button;

    public ToDoListGui(){
        super("To Do List");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // task list
        model = new DefaultListModel<>();
        task_list = new JList<>(model);
        // Scroll Pane
        JScrollPane listScrollPane = new JScrollPane(task_list);
        listScrollPane.setPreferredSize(new Dimension(300, 200));
        add(listScrollPane, BorderLayout.NORTH);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        // task entry
        task_entry = new JTextField();
        buttonPanel.add(task_entry);

        // add button
        add_button = new JButton("Add task");
        buttonPanel.add(add_button);
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTask = task_entry.getText();
                if (!newTask.isEmpty()){
                    model.addElement(newTask);
                    task_entry.setText("");
                }
            }
        });

        // remove button
        remove_button = new JButton("Remove task");
        buttonPanel.add(remove_button);
        remove_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = task_list.getSelectedIndex();
                if (index != -1){
                    model.remove(index);
                }
                else{
                    JOptionPane.showMessageDialog(ToDoListGui.this, "You must select a task.");
                }
            }
        });

        // save button
        save_button = new JButton("Save task");
        buttonPanel.add(save_button);
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTasksToFile();
            }
        });

        // load button
        load_button = new JButton("Load task");
        buttonPanel.add(load_button);
        load_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTasksFromFile();
            }
        });

        add(buttonPanel);
    }

    // Load task
    private void loadTasksFromFile() {
        model.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ertan\\IdeaProjects\\Java To Do List\\src\\tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                model.addElement(line);
            }
            JOptionPane.showMessageDialog(this, "Tasks loaded from file.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Save tasks
    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\ertan\\IdeaProjects\\Java To Do List\\src\\tasks.txt"))) {
            for (int i = 0; i < model.size(); i++) {
                writer.write(model.get(i));
                writer.newLine(); // Add a newline after each task
            }
            JOptionPane.showMessageDialog(this, "Tasks saved to file.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
