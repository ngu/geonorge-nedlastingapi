package no.geonorge.httpd;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.geonorge.nedlasting.data.User;
import no.geonorge.nedlasting.security.HttpBasicAuthentication;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        User user = HttpBasicAuthentication.getUser(req);
        if (user == null) {
            HttpBasicAuthentication.askForPassword((HttpServletRequest) req, (HttpServletResponse) resp);
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}
