package cinema.controller;

import cinema.model.Account;
import cinema.store.SqlStore;
import cinema.store.Store;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final static Store STORE = SqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String session = req.getSession().getId();
        Account account = STORE.getFromCache(session);
        List<Integer> list = account.getSeats();
        resp.setContentType("json");
        Gson gson = new Gson();
        gson.toJson(list, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String session = req.getSession().getId();
        Account account = STORE.getFromCache(session);
        String name = req.getParameter("username");
        String phone = req.getParameter("phone");
        account.setName(name);
        account.setPhone(phone);
        STORE.reserveSeats(account);
        STORE.deleteFromCache(session);
        req.getRequestDispatcher("complete.html").forward(req, resp);
    }
}
