package br.com.omdb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.omdb.service.OmdbService;

@WebServlet("/buscar-filme")
public class FilmeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OmdbService omdbService = new OmdbService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String jsonResponse = "";

            String id = request.getParameter("id");
            String titulo = request.getParameter("titulo");
            
            if(id != null && !id.isEmpty()) {
            	jsonResponse = omdbService.buscarDetalhesPorId(id);
            } else if(titulo != null && !titulo.isEmpty()) {
            	jsonResponse = omdbService.buscarFilmes(titulo);
            } else {
            	jsonResponse = "{\"Error\":\"Parâmetros inválidos.\"}";
            }
            
            out.print(jsonResponse);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar filme.");
        } finally {
        	out.flush();
        }
    }
}