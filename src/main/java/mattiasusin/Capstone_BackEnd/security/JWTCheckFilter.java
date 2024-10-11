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


		String accessToken = authHeader.substring(7); // "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MjY0ODE1MDMsImV4cCI6MTcyNzA4NjMwMywic3ViIjoiOTFkMTg2MGItZjE2Yy00MTYwLWIyYTYtODU2NWY0MzY5MTBiIn0.l58gBS6yJnRom0gYNRECl3W_e1B0TmdNkOivPncYP0fO3LIC2QXwvgft71jNYhfJ"

		jwtTools.verifyToken(accessToken);


		String id = jwtTools.extractIdFromToken(accessToken);
		Admin currentUser = this.adminsService.findByIdAdmin(UUID.fromString(id));


		Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication); // <-- Associo l'utente autenticato (Autentication) al Context


		filterChain.doFilter(request, response);

	}



	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// Fare l'override di questo metodo ci serve per 'disabilitare' questo filtro per alcune richieste,
		// ad esempio richieste su determinati endpoint oppure direttamente su determinati controller
		// Nel nostro caso ad esempio ci interessa che il filtro, che dovrà verificare i token, non venga chiamato in causa
		// per tutte le richieste di Login o di Register perché sono richieste che non devono richiedere un token per poter essere eseguite
		// Se gli endpoint di Login e Register si trovano nello stesso controller avranno lo stesso URL di base "http://localhost:3001/auth/**"

		// Posso quindi escludere dal controllo del filtro tutte le richieste verso gli endpoint che contengono /auth nell'URL
		return new AntPathMatcher().match("/auth/**", request.getServletPath())||
				new AntPathMatcher().match("/contatti/**", request.getServletPath())||
				new AntPathMatcher().match("/menu/view/**", request.getServletPath());
	}
}
