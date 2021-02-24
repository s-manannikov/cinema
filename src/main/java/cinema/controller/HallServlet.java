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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/seats")
public class HallServlet extends HttpServlet {
    private final static Store STORE = SqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        Gson gson = new Gson();
        List<Integer> occupiedSeats = STORE.getOccupiedSeats();
        gson.toJson(occupiedSeats, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (Objects.nonNull(req.getParameterValues("seat"))) {
            List<Integer> list = Arrays.stream(req.getParameterValues("seat"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            Account acc = new Account(list);
            HttpSession s = req.getSession();
            String str = s.getId();
            STORE.addToCache(str, acc);
        }
        resp.sendRedirect("index.html");
    }
}
