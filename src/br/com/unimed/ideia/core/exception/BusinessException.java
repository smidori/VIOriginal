package br.com.unimed.ideia.core.exception;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Classe de Exceção específica da aplicação
 * 
 * @author dgeison
 */
public class BusinessException extends Exception implements Serializable {

	private static final long serialVersionUID = -5565612478958589932L;

	private String message;

	/**
	 * Mantém os parâmetros do método que gerou a exceção.
	 */
	private Object[] paramsOfMethod;

	/**
	 * Classe a qual pertence o método que gerou a exceção.
	 */
	private Class<?> classe;

	/**
	 * Construtor default da Classe IntranetException
	 */
	public BusinessException() {
		paramsOfMethod = null;
		classe = null;
	}

	/**
	 * Contrutor simples da Classe IntranetException
	 * 
	 * @param e
	 *            - Exception.
	 */
	public BusinessException(Exception e) {
		super(e);
	}

	/**
	 * @param e
	 */
	public BusinessException(String cause) {
		super(new Exception(cause + "\n"));
		message = cause;
	}

	/**
	 * Construtor parcial da Classe IntranetException
	 * 
	 * @param e
	 *            - Exception
	 * @param paramsOfMethod
	 *            - Parâmetros que foram passados no método que lançou a
	 *            exceção.
	 */
	public BusinessException(Exception e, Object[] paramsOfMethod) {
		super(e);
		this.paramsOfMethod = paramsOfMethod;
	}

	/**
	 * Construtor completo da ClasseIntranetException
	 * 
	 * @param e
	 *            - Exception.
	 * @param paramsOfMethod
	 *            - Parâmetros que foram passados no método que lançou a
	 *            exceção.
	 * @param classe
	 *            - Classe a qual pertence o método que lançou a exceção.
	 */
	public BusinessException(Exception e, Object[] paramsOfMethod, Class<?> classe) {
		super(e);
		this.paramsOfMethod = paramsOfMethod;
		this.classe = classe;
	}

	/**
	 * Retorna apenas o nome do método.
	 * 
	 * @return String
	 */
	public String getMethodName() {
		return getStackTrace()[0].getMethodName();
	}

	/**
	 * Retorna o nome da classe que pertence o método que lançou a exceção.
	 * 
	 * @return String
	 */
	public String getMethodClass() {
		return getStackTrace()[0].getClassName();
	}

	/**
	 * Retorna a linha a qual iniciou a causa da Exception.
	 * 
	 * @return int
	 */
	public int getLineInitException() {
		return getStackTrace()[0].getLineNumber();
	}

	/**
	 * Método que retorna o estado, dos itens passados por parâmetro no método
	 * que lançou a exceção. Esse método é indicado para o envio de informações
	 * de exceção pertinente ao estado dos parâmetros passados em um método que
	 * lançou a exceção, via e-mail.
	 * 
	 * @return String
	 */
	public String getValuesAllParametersOfMethod() {
		String result = "";
		Class<?> klass = null;
		Method method = null;
		Class<?>[] parameterTypes = null;
		Method[] methods = new Method[0];

		if (paramsOfMethod != null) {
			for (int i = 0; i < paramsOfMethod.length; i++) {
				if (paramsOfMethod[i] != null) {
					klass = paramsOfMethod[i].getClass();

					if (klass != null) {
						methods = klass.getMethods();
					}

					final String typeKlass = klass == null ? "null" : klass.toString();

					result += "<tr bgcolor=\"#0033CC\">"
							+ "<td width=\"38%\"><font color=\"#FFFFFF\"> Paramentro: " + i + "</font></td>"
							+ "<td width=\"32%\"><font color=\"#FFFFFF\"> Tipo: " + typeKlass
							+ "</font></td>" + "<td width=\"30%\"><font color=\"#FFFFFF\"> Valor Geral: "
							+ paramsOfMethod[i] + "</font></td>" + "</tr>" + "<tr bgcolor=\"#999999\">"
							+ "<td colspan=\"3\">" + "<div align=\"center\">Detalhes do Objeto</div>"
							+ "</td>" + "</tr>";

					for (int j = 0; j < methods.length; j++) {
						try {
							parameterTypes = methods[j].getParameterTypes();
							if (parameterTypes != null && parameterTypes.length == 0) {
								try {
									if (klass != null) {
										method = klass.getMethod(methods[j].getName(), parameterTypes);
									}
								} catch (NoSuchMethodException e) {
									// trata exceção caso seja construtor
									method = null;
								}

								if (method != null) {
									Object obj = null;
									try {
										obj = method.invoke(paramsOfMethod[i], (Object[]) null);
									} catch (Exception e) {/* nao faz nada */
									}

									if (obj != null) {
										result += "<tr>" + "<td width=\"38%\">M&eacute;todo: "
												+ method.getName() + "</td>" + "<td colspan=\"2\">Valor: "
												+ obj.toString() + "</td>" + "</tr>";
									} else {
										result += "<tr>" + "<td width=\"38%\">M&eacute;todo: "
												+ method.getName() + "</td>"
												+ "<td colspan=\"2\">Valor: null </td>" + "</tr>";
									}
								}
							}
						} catch (Exception e) {
							System.err.println(e.getStackTrace());
							System.err.println(e.getCause());
							Logger.getLogger(BusinessException.class).error("Erro ao enviar e-mail.", e);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Retona a classe.
	 * 
	 * @return Class
	 */
	public Class<?> getClasse() {
		return classe;
	}

	/**
	 * Seta a classe.
	 * 
	 * @param classe
	 */
	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	/**
	 * Retorna os parâmetros.
	 * 
	 * @return Object[]
	 */
	public Object[] getParamsOfMethod() {
		return paramsOfMethod;
	}

	/**
	 * Seta os parâmetros.
	 * 
	 * @param paramsOfMethod
	 */
	public void setParamsOfMethod(Object[] paramsOfMethod) {
		this.paramsOfMethod = paramsOfMethod;
	}

	/**
	 * Retorna em formato string a pilha da exceção.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("");
		if (getCause() != null) {
			buffer.append(getCause().getLocalizedMessage()).append('\n');
			for (int i = 0; i < getCause().getStackTrace().length; i++) {
				buffer.append(getCause().getStackTrace()[i]).append('\n');
			}
		}
		return buffer.toString();
	}

	/**
	 * Retorna HTML para o stacktrace gerado pela exception
	 * 
	 * @return String
	 */
	public String toHtml() {
		StringBuilder buffer = new StringBuilder("");
		if (getCause() != null) {
			buffer.append(getCause().getLocalizedMessage()).append("<br>");
			for (int i = 0; i < getCause().getStackTrace().length; i++) {
				buffer.append(getCause().getStackTrace()[i]).append("<br>");
			}
		}
		return buffer.toString();
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
