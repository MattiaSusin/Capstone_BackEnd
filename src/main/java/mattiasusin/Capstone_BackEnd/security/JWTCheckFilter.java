package mattiasusin.Capstone_BackEnd.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mattiasusin.Capstone_BackEnd.entities.Admin;
import mattiasusin.Capstone_BackEnd.exceptions.UnauthorizedException;
import mattiasusin.Capstone_BackEnd.services.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {
	@Autowired
	private JWTTools jwtTools;
	@Autowired
	private AdminsService adminsService;


	// ADMIN

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore inserisci correttamente il token nell'Authorization Header");


		String accessToken = authHeader.substring(7);

		jwtTools.verifyToken(accessToken);


		String id = jwtTools.extractIdFromToken(accessToken);
		Admin currentUser = this.adminsService.findByIdAdmin(UUID.fromString(id));


		Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication); // <-- Associo l'utente autenticato (Autentication) al Context


		filterChain.doFilter(request, response);

	}



	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		return new AntPathMatcher().match("/auth/**", request.getServletPath())||
				new AntPathMatcher().match("/contatti/**", request.getServletPath())||
				new AntPathMatcher().match("/menu/filtro/**", request.getServletPath())||
				new AntPathMatcher().match("/drinks/filtro/**", request.getServletPath())||
				new AntPathMatcher().match("/drinks/view/**", request.getServletPath())||
				new AntPathMatcher().match("/menu/view/**", request.getServletPath());
	}
}
