package hostelmanagementsystem;

import hostelmanagementsystem.network.HostelServer;
import hostelmanagementsystem.view.LoginForm;
import javax.swing.SwingUtilities;

/**
 * EXECUTION FLOW:
 *  1. Server Starts  (background thread, port 5000)
 *  2. Login Form Opens
 *  3. Auth Check     (LoginController queries DB)
 *  4. Role Redirect  (Admin → AdminDashboard | Student → StudentDashboard)
 *  5. CRUD Execution (StudentController executes INSERT/UPDATE/DELETE/SELECT)
 */
public class HostelManagementSystem {

    public static void main(String[] args) {
        // Step 1 – Start socket server in background
        new Thread(() -> HostelServer.main(new String[]{}), "HostelServerThread").start();

        // Step 2 – Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
