package uk.ac.cardiff.team5.graphium.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Autowired
        private UserRepository userRepository;

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        public MySimpleUrlAuthenticationSuccessHandler() {
                super();
        }

        // API

        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
                handle(request, response, authentication);
        }

        // IMPL

        protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
                final String targetUrl = determineTargetUrl(authentication);

                if (response.isCommitted()) {
                        return;
                }

                redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        protected String determineTargetUrl(final Authentication authentication) {
                UserEntity user = userRepository.findByUsername(authentication.getName());
                System.out.println(user.getRole());
                Map<String, String> roleTargetUrlMap = new HashMap<>();
                        roleTargetUrlMap.put("1", "/");
                roleTargetUrlMap.put("2", "/admin/home");
                roleTargetUrlMap.put("3", "/sys-admin");
                        if(roleTargetUrlMap.containsKey(user.getRole())) {
                                return roleTargetUrlMap.get(user.getRole());
                }
                throw new IllegalStateException();
        }


}