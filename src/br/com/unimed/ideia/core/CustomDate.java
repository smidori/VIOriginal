package br.com.unimed.ideia.core;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import br.com.unimed.ideia.core.exception.BusinessException;

public final class CustomDate implements Serializable {

	private static final long serialVersionUID = -3468711558214985146L;

	private static final Logger LOG = Logger.getLogger(CustomDate.class);

	/**
	 * Opção Date para formato de data retornado.
	 */
	public static final String formatDate = "dd/MM/yyyy";
	/**
	 * Opção Timestamp para formato de data retornado.
	 */
	private static final Locale locale = new Locale("pt", "BR");
	private static final DateFormatSymbols dfs = new DateFormatSymbols(locale);

	public static final String formatDateTime = "dd/MM/yyyy HH:mm:ss";
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, locale);
	private static final SimpleDateFormat simpleHHmmFormat = new SimpleDateFormat("HH:mm", locale);

	private static final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(formatDateTime, locale);

	private java.util.Date data;
	private String dia;
	private String mes;
	private String ano;

	/**
	 * @param data
	 */
	public CustomDate(String data) {
		super();

		if (data != null && data.length() == formatDate.length()) {
			this.data = parseDate(data);
			Calendar calendar = getCalendar(this.data);
			this.data = calendar.getTime();
			dia = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			mes = Integer.toString(calendar.get(Calendar.MONTH) + 1);

			dia = (dia.trim().length() < 2) ? ("0" + dia.trim()) : dia.trim();
			mes = (mes.trim().length() < 2) ? ("0" + mes.trim()) : mes.trim();

			ano = Integer.toString(calendar.get(Calendar.YEAR));

			calendar = null;
			data = null;
		}
	}

	/**
	 * @param dia
	 * @param mes
	 * @param ano
	 */
	public CustomDate(String dia, String mes, final String ano) {

		super();

		if (dia != null && dia.trim().length() > 0 && mes != null && mes.trim().length() > 0 && ano != null
				&& ano.trim().length() > 0 && ano.trim().length() == 4) {
			if (dia.trim().length() < 2) {
				dia = "0" + dia.trim();
			}
			if (mes.trim().length() < 2) {
				mes = "0" + mes.trim();
			}
			this.dia = dia;
			this.mes = mes;
			this.ano = ano.trim();
			data = parseDate(this.dia + "/" + this.mes + "/" + this.ano);
		}
	}

	/**
	 * @return String
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @return java.util.Date
	 */
	public java.util.Date getData() {
		return data;
	}

	/**
	 * @return String
	 */
	public String getStringData() {
		return data != null ? (formatDate(data)) : null;
	}

	/**
	 * @return String
	 */
	public String getDia() {
		return dia;
	}

	/**
	 * @return String
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * Obtém a data atual do sistema.
	 *
	 * @return Date
	 */
	public synchronized static final Date getCurrentDate() {
		return getCalendar(null).getTime();
	}

	public synchronized static java.sql.Date getCurrentSqlDate() {
		return parseSqlDate(getCurrentDate());
	}

	public synchronized static java.sql.Timestamp getCurrentTimestamp() {
		return parseTimestamp(getCurrentDate());
	}

	/**
	 * Obtém o ano atual.
	 */
	public synchronized static final int getYear() {
		final Calendar calendar = getCalendar(null);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Obtém a hora atual do sistema
	 */
	public synchronized static final int getHora() {
		final Calendar calendar = getCalendar(null);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Obtém o minuto atual do sistema
	 */
	public synchronized static final int getMinuto() {
		final Calendar calendar = getCalendar(null);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * Obtém o segundo atual do sistema
	 */
	public synchronized static final int getSegundo() {
		final Calendar calendar = getCalendar(null);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @param sData
	 *            data String no formato 'dd/MM/yyyy' ou 'dd/MM/yyyy HH:mm:ss'
	 * @return
	 */
	public synchronized final static Calendar parseCalendar(final String sData) {
		return getCalendar(parseDate(sData));
	}

	/**
	 * Retorna um Calendar com TIME_ZONE configurado.
	 * Passar null para obter na data atual.
	 * @param data
	 *            java.util.Date
	 * @return Calendar
	 */
	public synchronized final static Calendar getCalendar(final java.util.Date data) {
		// TimeZone.setDefault(TIME_ZONE);
		final Calendar calendar = Calendar.getInstance(locale);
		// calendar.setTimeZone(TimeZone.getDefault());
		if (data != null) {
			calendar.setTime(data);
		}
		return calendar;
	}

	/**
	 * Adiciona o número de dias a data.
	 *
	 * @param data
	 * @param nroDias
	 * @return Date
	 */
	public synchronized final static Date addDiasDate(final java.util.Date data, final int nroDias) {
		return addInDate(data, nroDias, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Adiciona o número de meses a data.
	 *
	 * @param data
	 * @param nroMeses
	 * @return Date
	 */
	public synchronized final static Date addMonthsDate(final java.util.Date data, final int nroMeses) {
		return addInDate(data, nroMeses, Calendar.MONTH);
	}

	/**
	 * Seta o valor da data em horas, dias, meses ou anos
	 *
	 * @param data
	 * @param hours
	 * @return Date
	 */
	public synchronized final static Date setInDate(final java.util.Date data, final int nroAdd, final int FIELD) {
		final Calendar calendar = getCalendar(data);
		calendar.set(FIELD, nroAdd);
		return calendar.getTime();
	}

	/**
	 * Incrementa o valor da data em dias, meses ou anos.
	 *
	 * @param data
	 *            Date a ser incrementada.
	 * @param nroAdd
	 *            Quantidade a ser incrementada.
	 * @param FIELD
	 *            Opção para incremento. Ex: Calendar.DAY_OF_MONTH,
	 *            Calendar.MONTH, Calendar.YEAR
	 * @return Date
	 */
	public synchronized final static Date addInDate(final java.util.Date data, final int nroAdd, final int FIELD) {
		final Calendar calendar = getCalendar(data);
		calendar.add(FIELD, nroAdd);
		return calendar.getTime();
	}

	/**
	 * Obtém uma data no format String 'dd/MM/yyyy'
	 *
	 * @param date
	 * @return String
	 */
	public synchronized static final String formatDate(final java.util.Date date) {
		String dateFormat = null;

		if (date != null) {
			dateFormat = simpleDateFormat.format(date);
		}

		return dateFormat;
	}

	/**
	 * Obtém uma data no format String 'dd/MM/yyyy'
	 *
	 * @param date
	 * @return String
	 */
	public synchronized static final String formatDate(final java.util.Calendar date) {
		String dateFormat = null;

		if (date != null) {
			dateFormat = simpleDateFormat.format(date.getTime());
		}

		return dateFormat;
	}

	/**
	 * Obtém uma data no format String 'HH:mm'
	 *
	 * @param date
	 * @return String
	 */
	public synchronized static final String formatHHmm(final java.util.Date date) {
		String dateFormat = null;

		if (date != null) {
			dateFormat = simpleHHmmFormat.format(date);
		}

		return dateFormat;
	}

	/**
	 * Obtém uma data no format String 'dd/MM/yyyy HH:mm:ss'
	 *
	 * @param date
	 * @return String
	 */
	public synchronized static final String formatDateTime(final java.util.Date date) {
		String dateTimeFormat = null;

		if (date != null) {

			dateTimeFormat = simpleDateTimeFormat.format(date);
		}

		return dateTimeFormat;
	}

	/**
	 * Obtém um objeto java.util.Date a partir de uma data String no format
	 * 'dd/MM/yyyy HH:mm:ss'.
	 *
	 * @param dateFormatHHmmss
	 * @return java.util.Date
	 */
	public synchronized static final java.util.Date parseDateHHmmss(final String dateFormatHHmmss) {
		if (dateFormatHHmmss != null && dateFormatHHmmss.length() == formatDateTime.length()) {
			try {
				//dateFormatHHmmss = dateFormatHHmmss.replace('-', '/');
				return getCalendar(simpleDateTimeFormat.parse(dateFormatHHmmss)).getTime();
			} catch (final ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Retorna um objeto java.util.Date no formato 'dd/MM/yyyy'
	 *
	 * @param dateStr
	 *            data String no formato 'dd/MM/yyyy' ou 'dd/MM/yyyy HH:mm:ss'
	 * @return java.util.Date
	 */
	public synchronized static final java.util.Date parseDate(final String dateStr) {
		if (dateStr != null) {
			if (dateStr.length() == formatDate.length()) {
				try {
					return getCalendar(simpleDateFormat.parse(dateStr)).getTime();
				} catch (final ParseException e) {
					e.printStackTrace();
				}
			}
			if ( dateStr.length() == formatDateTime.length()) {
				try {
					return getCalendar(simpleDateTimeFormat.parse(dateStr)).getTime();
				} catch (final ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	/**
	 * @param dateTime data String no formato 'dd/MM/yyyy' ou 'dd/MM/yyyy HH:mm:ss'
	 * @return
	 */
	public synchronized static java.sql.Timestamp parseTimestamp(final String dateTime) {
		return parseTimestamp(parseDate(dateTime));
	}

	/**
	 * @param dateTime
	 * @return
	 */
	public synchronized static java.sql.Timestamp parseTimestamp(final java.util.Date dateTime) {
		if (dateTime == null) {
			return null;
		}

		return new java.sql.Timestamp(dateTime.getTime());
	}

	/**
	 * @param date
	 * @return
	 */
	public synchronized static java.sql.Date parseSqlDate(final java.util.Date date) {
		if (date == null) {
			return null;
		}

		return new java.sql.Date(date.getTime());
	}

	/**
	 * @param date
	 * @return
	 */
	public synchronized static java.sql.Date parseSqlDate(final String date) {
		return parseSqlDate(parseDate(date));
	}

	/**
	 * Retorna um objeto java.sql.Date no formato 'dd/MM/yyyy'
	 *
	 * @param dateFormat
	 *            data String no formato 'dd/MM/yyyy' ou 'dd/MM/yyyy HH:mm:ss'
	 * @param formatType
	 * @return java.sql.Date
	 */
	public synchronized static final java.sql.Date parseSqlDate(final String dateFormat, final String formatType) {
		java.sql.Date sqlDate = null;

		if (dateFormat != null && formatType != null) {
			try {
				if ((formatDate.equals(formatType) && dateFormat.length() == formatDate.length())
						|| (formatDateTime.equals(formatType) && dateFormat.length() == formatDateTime
						.length())) {
					Calendar calendar = getCalendar(new SimpleDateFormat(formatType, locale)
					.parse(dateFormat));
					sqlDate = new java.sql.Date(calendar.getTimeInMillis());
					calendar = null;
				}
			} catch (final ParseException e) {
				e.printStackTrace();
			}
		}
		return sqlDate;

	}

	/**
	 * Verifica se o objeto java.util.Date passado como parâmetro é um Sábado ou
	 * um Domingo e retorna o nome dia como string. Se o parâmetro Date for
	 * null, assume a data atual.
	 *
	 * @param data
	 * @return String
	 * @throws Exception
	 */
	public synchronized static final String getWeekendName(java.util.Date data) throws Exception {
		if (data == null) {
			data = getCurrentDate();
		}

		Calendar ca = getCalendar(data);
		final int dia = ca.get(Calendar.DAY_OF_WEEK);
		ca = null;

		if (dia == Calendar.SUNDAY) {
			return "Domingo";
		} else if (dia == Calendar.SATURDAY) {
			return "Sábado";
		}
		return null;
	}

	/**
	 * Método que retorna a descrição do dia da semana de acordo com a data
	 * passada como parametro.
	 *
	 * @param data
	 * @return String
	 */
	public synchronized static final String getWeekDay(final java.util.Date data) {
		if (data == null) {
			return "";
		}
		Calendar ca = getCalendar(data);
		final int diaSemana = ca.get(Calendar.DAY_OF_WEEK);
		ca = null;
		final String[] diasDaSemana = dfs.getWeekdays();
		return diasDaSemana[diaSemana];
	}

	/**
	 * Método que retorna a descrição do Mês de acordo com a data passada como
	 * parametro.
	 *
	 * @param data
	 * @return String
	 */
	public synchronized static final String getMonthYear(final java.util.Date data) {
		if (data == null) {
			return "";
		}
		Calendar ca = getCalendar(data);
		final int mes = ca.get(Calendar.MONTH);
		ca = null;
		final String[] meses = dfs.getMonths();
		return meses[mes];
	}

	public synchronized static final String getDayMonth(final java.util.Date data) {
		if (data == null) {
			return "";
		}
		final Calendar ca = getCalendar(data);
		final int dia = ca.get(Calendar.DAY_OF_MONTH);
		final int mes = ca.get(Calendar.MONTH) + 1;
		return String.valueOf(dia) + "/" + String.valueOf(mes);
	}

	/**
	 * Retorna o numero de anos entre as datas.
	 * @param initDate
	 * @param endDate
	 * @return
	 */
	public synchronized static final int getNumberYears(Calendar initDate, Calendar endDate) {
		if (initDate == null || endDate == null) {
			return 0;
		}
		long milis = (endDate.getTimeInMillis() - initDate.getTimeInMillis());
		initDate = null;
		endDate = null;

		milis += 3600000L;// 1 hora para compensar horário de verão
		return (int) (milis / 31536000000L);// milissegundos por ano
	}

	/**
	 * Método que retorna o número de dias entre duas datas passadas como
	 * parametro.
	 *
	 * @param initDate
	 * @param endDate
	 * @return int
	 */
	public synchronized static final int getNumberDays(final java.util.Date initDate, final java.util.Date endDate) {
		if (initDate == null || endDate == null) {
			return 0;
		}
		final Calendar c1 = getCalendar(initDate);
		final Calendar c2 = getCalendar(endDate);
		return getNumberDays(c1, c2);
	}

	/**
	 * Método que retorna o número de dias entre duas datas passadas como
	 * parametro.
	 *
	 * @param initDate
	 * @param endDate
	 * @return int
	 */
	public synchronized static final int getNumberDays(Calendar c1, Calendar c2) {
		if (c1 == null || c2 == null) {
			return 0;
		}
		long milis = (c2.getTimeInMillis() - c1.getTimeInMillis());
		c1 = null;
		c2 = null;

		milis += 3600000L;// 1 hora para compensar horário de verão
		return (int) (milis / 86400000);// milissegundos por dia
	}

	/**
	 * Método que retorna String com a diferença de dias/horas/minutos entre
	 * duas datas passadas como parametro.
	 *
	 * @param initDate
	 * @param endDate
	 * @return String
	 */
	public synchronized static final String getNumberDaysHours(final java.util.Date initDate, final java.util.Date endDate) {
		if (initDate == null || endDate == null) {
			return null;
		}
		final Calendar c1 = getCalendar(initDate);
		final Calendar c2 = getCalendar(endDate);
		final long milis = (c2.getTimeInMillis() - c1.getTimeInMillis());

		final StringBuffer dif = new StringBuffer();

		if ((milis / 86400000) > 0) {
			dif.append(String.valueOf(milis / 86400000));
			dif.append(" dia(s) ");
		}
		if (((milis / 1000 / 60 / 60) % 24) > 0) {
			dif.append((milis / 1000 / 60 / 60) % 24);
			dif.append(" hora(s) ");
		}
		dif.append((milis / 1000 / 60) % 60);
		dif.append(" minuto(s)");
		return dif.toString();
	}

	/**
	 * Metodo que retorna o total de Sábados e Domingos em um determinado
	 * intervalo.
	 *
	 * @param dataInicial
	 *            Data inicial do intervalo.
	 * @param dataFinal
	 *            Data final do intervalo.
	 * @return int
	 */
	public synchronized final static int getTotalWeekend(final java.util.Date dataInicial, final java.util.Date dataFinal) {
		final Calendar calIni = getCalendar(dataInicial);
		final Calendar calFinal = getCalendar(dataFinal);
		return calcNumberWeekend(calIni, calFinal, 0);
	}

	/**
	 * Metodo que calcula o número de Sábados e Domingos em um determinado
	 * intervalo e retorna o resultado.
	 *
	 * @param calIni
	 * @param calFinal
	 * @param weekends
	 * @return int
	 */
	private synchronized final static int calcNumberWeekend(Calendar calIni, Calendar calFinal, int weekends) {
		if (calIni.getTime().compareTo(calFinal.getTime()) <= 0) {
			final int dia = calIni.get(Calendar.DAY_OF_WEEK);
			if (dia == Calendar.SUNDAY) {
				calIni.add(Calendar.DAY_OF_MONTH, 6);
				weekends++;
			} else if (dia == Calendar.SATURDAY) {
				calIni.add(Calendar.DAY_OF_MONTH, 1);
				weekends++;
			} else {
				while (calIni.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
					calIni.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
			weekends = +calcNumberWeekend(calIni, calFinal, weekends);
		}
		calIni = null;
		calFinal = null;

		return weekends;
	}

	/**
	 * Verifica se o objeto java.util.Date passado como parâmetro é um Sábado ou
	 * um Domingo. Se o parâmetro Date for null, retorna false.
	 *
	 * @param data
	 * @return boolean
	 * @throws Exception
	 */
	public synchronized static final boolean isWeekend(final java.util.Date data) throws Exception {
		if (data == null) {
			return false;
		}

		Calendar ca = getCalendar(data);
		final int dia = ca.get(Calendar.DAY_OF_WEEK);
		ca = null;
		return (dia == Calendar.SUNDAY || dia == Calendar.SATURDAY);
	}

	/**
	 * Método toString().
	 *
	 * @return String
	 */
	@Override
	public synchronized String toString() {
		return getDia() + "/" + getMes() + "/" + getAno();
	}

	/**
	 * Verifica se a String passada como parâmetro é uma data válida. Se o
	 * parâmetro for null, retorna false.
	 *
	 * @param data
	 * @return boolean
	 */
	public synchronized static final boolean isDate(final String data) {
		return (CustomDate.parseDate(data) != null);

	}

	/**
	 * Método que calcula a idade com base no parametro data de nascimento.
	 *
	 * @param dataNascimento
	 * @return A idade como inteiro.
	 */
	@Deprecated
	public synchronized static final Integer getAge(final Date dataNascimento) {
		if (dataNascimento == null) {
			return null;
		}

		final long birthday = dataNascimento.getTime();
		final Calendar age = getCalendar(null);
		age.setTimeInMillis(Math.abs(birthday - System.currentTimeMillis()));
		return Integer.valueOf(age.get(Calendar.YEAR) - 1970);
	}

	/**
	 * Método que calcula a idade com base no parametro data de nascimento.
	 *
	 * @param dataNascimento
	 * @return A idade como inteiro.
	 */
	public synchronized static final int getIdade(final Date date) {
		final Calendar birthdate = getCalendar(null);
		birthdate.setTime(date);
		final Calendar now = getCalendar(null);
		int age = now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
		birthdate.add(Calendar.YEAR, age);
		if (now.before(birthdate)) {
			age--;
		}
		return age;
	}

	/**
	 * Método que calcula o tempo em horas/minuto/segundo com base no parametro
	 * tempo em milisegundos.
	 *
	 * @param tempo
	 * @return tempo no formato HH:mm:ss.
	 */
	public synchronized static final String time(final double tempo) {
		final double hr = (tempo / (1000d * 60d * 60d));
		final double min = (tempo / (1000d * 60d)) % 60;
		final double seg = (tempo / (1000d)) % 60;

		return convert(hr) + ":" + convert(min) + ":" + convert(seg);
	}

	/**
	 * Método que calcula o tempo em dias/horas/minuto/segundo com base no
	 * parametro tempo em milisegundos, descontando os finais de semana.
	 *
	 * @param tempo
	 * @return tempo no formato HH:mm:ss.
	 */
	public synchronized static final String time(double tempo, final double weekend) {
		double dia, hr, min, seg;

		if (tempo > 86400000) {
			tempo = tempo - weekend;
			dia = (tempo / (1000 * 60 * 60 * 24));
			hr = ((dia - (int) dia) * 24);
			min = ((hr - (int) hr) * 60);
			seg = (int) ((min - (int) min) * 60);
			return convert(dia) + "d " + convert(hr) + ":" + convert(min) + ":" + convert(seg);
		}
		return time(tempo);
	}

	/**
	 * função que converte um valor de tempo de double para long
	 *
	 * @param vlr
	 * @return
	 */
	public synchronized static final String convert(final double vlr) {
		final Long l = Long.valueOf(new Double(vlr).longValue());
		return l.toString().length() >= 2 ? l.toString() : ("0" + l.toString());
	}

	/**
	 * Valida se exista data de exclusão e se esta é passada ou futura.
	 *
	 * @param data
	 * @return String
	 */
	public synchronized static String dataExclusion(final String name, final Date dataExclusion) {
		final String msg[] = { "?1 foi excluído do sistema em ?2", "?1 com data de exclusão programada para ?2" };

		if (dataExclusion == null) {
			return "NULL";
		}

		if (new Date(dataExclusion.getTime()).compareTo(CustomDate.getCurrentDate()) < 0) {
			return msg[0].replace("?1", name).replace("?2", formatDate(dataExclusion));
		}
		return msg[1].replace("?1", name).replace("?2", formatDate(dataExclusion));
	}

	/**
	 * Verifica se o valor da date desta instância de Date é igual ao de uma
	 * outra instância.
	 *
	 * @param date
	 *            data do tipo Date, no formato(aaaa,MM,dd)
	 * @param otherDate
	 * @return True, se a data da Date for igual a uma outra instância
	 */
	public synchronized static boolean compare(final Date date, final Date otherDate) {
		return formatDate(date).equals(formatDate(otherDate));
	}

	public synchronized static boolean isSameYear(final Date dateA, final Date dateB) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(dateA);
		final int yearA = cal.get(Calendar.YEAR);
		cal.setTime(dateB);
		final int yearB = cal.get(Calendar.YEAR);

		return yearA == yearB ? true : false;
	}

	/**
	 * Configura a hora/min/seg/mili para meia noite (00:00:00:0000) no dia informado
	 *
	 * @param date
	 * @return
	 */
	public synchronized static Date setMidnightInDate(Date date) {
		date = setInDate(date, 0, Calendar.HOUR_OF_DAY); //Calendar.HOUR estava colocando meio dia
		date = setInDate(date, 0, Calendar.MINUTE);
		date = setInDate(date, 0, Calendar.SECOND);
		date = setInDate(date, 0, Calendar.MILLISECOND);

		return date;
	}

	/**
	 * Configura a data com o ultimo minuto do dia. (23:59:59)
	 * @param date
	 * @return
	 */
	public synchronized static Date setEndDay(Date date) {
		if (date != null) {
			date = setInDate(date, 23, Calendar.HOUR_OF_DAY); //Calendar.HOUR estava colocando meio dia
			date = setInDate(date, 59, Calendar.MINUTE);
			date = setInDate(date, 59, Calendar.SECOND);
			date = setInDate(date, 999, Calendar.MILLISECOND);
		}
		return date;
	}

	/**
	 * Retorna a Zero Hora do dia seguinte da data informada.
	 *
	 * @param date
	 * @return
	 */
	public synchronized static Date setMidnightNextDay(Date date) {
		if (date != null) {
			date = addDiasDate(date, 1);
			date = setMidnightInDate(date);
			//			date = setInDate(date, 23, Calendar.HOUR);
			//			date = setInDate(date, 59, Calendar.MINUTE);
			//			date = setInDate(date, 59, Calendar.SECOND);
			//			date = setInDate(date, 999, Calendar.MILLISECOND);
		}
		return date;
	}

	/**
	 * Configura o horário em uma data.
	 *
	 * @param date
	 * @param hora
	 * @param minuto
	 * @return
	 */
	public synchronized static Timestamp setHourInDate(Date date, final int hora, final int minuto) {
		date = setInDate(date, hora, Calendar.HOUR_OF_DAY);
		date = setInDate(date, minuto, Calendar.MINUTE);
		date = setInDate(date, 0, Calendar.SECOND);
		date = setInDate(date, 0, Calendar.MILLISECOND);

		final Timestamp nDate = new Timestamp(date.getTime());

		return nDate;
	}

	/**
	 * Converte data para Timestamp
	 *
	 * @param date
	 * @return
	 */
	public synchronized static final Timestamp parseDate(final Date date) {
		if (date != null) {
			final Timestamp timestamp = new Timestamp(date.getTime());
			return timestamp;
		}
		return null;
	}

	public synchronized static final Date getFirstDayNextMonth() {
		final Calendar ca = getCalendar(null);
		final int mes = ca.get(Calendar.MONTH) + 1;
		final int ano = ca.get(Calendar.YEAR);

		final Calendar c1 = new GregorianCalendar(ano, mes, 1);
		return c1.getTime();
	}

	public synchronized static java.sql.Date toSqlDate(final Calendar date) {
		return new java.sql.Date(date.getTimeInMillis());
	}

	public synchronized static java.sql.Date toSqlDate(final java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * Método que verifica se a data se encontra dentro do periodo informado
	 * (incluive).
	 *
	 * @author f19650 - Alex Kayser
	 * @param date
	 *            Data a ser verificada. Aceita objetos java.util.Date,
	 *            java.sql.Date e Calendar.
	 * @param ini
	 *            Data de inicio do período a ser verificado. Aceita objetos
	 *            java.util.Date, java.sql.Date e Calendar.
	 * @param fim
	 *            Data de termino do período a ser verificado. Aceita objetos
	 *            java.util.Date, java.sql.Date e Calendar.
	 * @return true se a data (primeiro argumento) se encontra entre o período
	 *         informado (segundo e terceiro parametro).
	 */
	public synchronized static boolean isInPeriod(final Object date, final Object ini, final Object fim) {
		long lDate = 0;
		long lIni = 0;
		long lFim = 0;

		if (date == null) {
			throw new IllegalArgumentException("Argumento date não pode ser nulo.");
		}
		if (ini == null) {
			throw new IllegalArgumentException("Argumento ini não pode ser nulo.");
		}
		if (fim == null) {
			throw new IllegalArgumentException("Argumento fim pode ser nulo.");
		}

		// date
		if (date instanceof java.util.Date) {
			lDate = ((java.util.Date) date).getTime();
		} else if (date instanceof java.sql.Date) {
			lDate = ((java.sql.Date) date).getTime();
		} else if (date instanceof Calendar) {
			lDate = ((Calendar) date).getTimeInMillis();
		} else {
			throw new IllegalArgumentException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 1)");
		}

		// ini
		// Ticket 47344 - 16/07/2015 - erro para achar parcial
		if (ini instanceof java.util.Date) {
			final java.util.Date iniC = (java.util.Date) ini;
			lIni = iniC.getTime();
		} else if (ini instanceof java.sql.Date) {
			final java.sql.Date iniC = (java.sql.Date) ini;
			lIni = iniC.getTime();
		} else if (ini instanceof Calendar) {
			final Calendar iniC = (Calendar) ini;
			lIni = iniC.getTimeInMillis();
		} else {
			throw new IllegalArgumentException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 2)");
		}

		// fim (subtrai 1 milesimo para se manter no mesmo dia.
		// Ticket 47344 - 16/07/2015 - erro para achar parcial
		if (fim instanceof java.util.Date) {
			final java.util.Date fimC = (java.util.Date) fim;
			lFim = fimC.getTime() - 1;
		} else if (fim instanceof java.sql.Date) {
			final java.sql.Date fimC = (java.sql.Date) fim;
			lFim = fimC.getTime() - 1;
		} else if (fim instanceof Calendar) {
			final Calendar fimC = (Calendar) fim;
			lFim = fimC.getTimeInMillis() - 1;
		} else {
			throw new IllegalArgumentException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 3)");
		}

		/* se o período vier invertido, destroca.
		if (lFim < lIni) {
			long aux = lFim;
			lFim = lIni;
			lIni = aux;
		} */

		return lDate >= lIni && lDate <= lFim;
	}

	/**
	 * Verifica se os periodos são coincidentes (sobrepostos).
	 * @param iniA inicio do periodo A
	 * @param fimA fim do periodo A
	 * @param iniB inicio do periodo B
	 * @param fimB fim do periodo B
	 * @return retorna true se são periodos sobrepostos (não válidos)
	 * @throws BusinessException
	 */
	public synchronized static boolean periodCoinciding(final Object iniA, final Object fimA, final Object iniB, final Object fimB) throws BusinessException {
		long lIniA = 0;
		long lFimA = 0;
		long lIniB = 0;
		long lFimB = 0;

		if (iniA == null) {
			//throw new BusinessException("Argumento iniA não pode ser nulo.");
			LOG.warn("Argumento iniA não pode ser nulo.");
			return false;
		} if (fimA == null) {
			//throw new BusinessException("Argumento fimA não pode ser nulo.");
			LOG.warn("Argumento fimA não pode ser nulo.");
			return false;
		} if (iniB == null) {
			//throw new BusinessException("Argumento iniB não pode ser nulo.");
			LOG.warn("Argumento iniB não pode ser nulo.");
			return false;
		} if (fimB == null) {
			//throw new BusinessException("Argumento fimB não pode ser nulo.");
			LOG.warn("Argumento fimB não pode ser nulo.");
			return false;
		}

		if (iniA instanceof java.util.Date) {
			lIniA = ((java.util.Date) iniA).getTime();
		} else if (iniA instanceof java.sql.Date) {
			lIniA = ((java.sql.Date) iniA).getTime();
		} else if (iniA instanceof Calendar) {
			lIniA = ((Calendar) iniA).getTimeInMillis();
		} else {
			throw new BusinessException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 1)");
		}

		if (fimA instanceof java.util.Date) {
			lFimA = ((java.util.Date) fimA).getTime();
		} else if (fimA instanceof java.sql.Date) {
			lFimA = ((java.sql.Date) fimA).getTime();
		} else if (fimA instanceof Calendar) {
			lFimA = ((Calendar) fimA).getTimeInMillis();
		} else {
			throw new BusinessException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 2)");
		}

		if (iniB instanceof java.util.Date) {
			lIniB = ((java.util.Date) iniB).getTime();
		} else if (iniB instanceof java.sql.Date) {
			lIniB = ((java.sql.Date) iniB).getTime();
		} else if (iniB instanceof Calendar) {
			lIniB = ((Calendar) iniB).getTimeInMillis();
		} else {
			throw new BusinessException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 3)");
		}

		if (fimB instanceof java.util.Date) {
			lFimB = ((java.util.Date) fimB).getTime();
		} else if (fimB instanceof java.sql.Date) {
			lFimB = ((java.sql.Date) fimB).getTime();
		} else if (fimB instanceof Calendar) {
			lFimB = ((Calendar) fimB).getTimeInMillis();
		} else {
			throw new BusinessException(
					"Metodo aceita objetos java.util.Date, java.sql.Date e Calendar. (argumento 4)");
		}

		//Verificar se data inicial é menor que data final
		if (lIniA > lFimA || lIniB > lFimB) {
			throw new BusinessException("Data inicial maior que data final");
		}

		//Verificar se existe sobreposição
		//InicioB começando antes de InicioA e FimB depois de InicioA
		final boolean condicao1 = (lIniB <= lIniA && lFimB >= lIniA);
		//InicioB começando depois de InicioA e antes de FimA
		final boolean condicao2 = (lIniB >= lIniA && lIniB <= lFimA);
		//InicioA começando antes de InicioB e FimA depois de InicioB
		final boolean condicao3 = (lIniA <= lIniB && lFimA >= lIniB);
		//InicioA começando depois de InicioB e antes de FimB
		final boolean condicao4 = (lIniA >= lIniB && lIniA <= lFimB);

		return condicao1 || condicao2 || condicao3 || condicao4;
	}

	public synchronized static Calendar setMidnightNextDay(final Calendar dt) {
		Date date = dt.getTime();
		date = setMidnightNextDay(date);
		return getCalendar(date);
	}

	public synchronized static Calendar setMidnightInDate(final Calendar dt) {
		Date date = dt.getTime();
		date = setMidnightInDate(date);
		return getCalendar(date);
	}


	/**
	 * Devido a um bug do metodo toGregorianCalendar, da classe
	 * XMLGregorianCalendar, tive de implementar este.
	 * O bug estava jogando várias datas para 01/01/1970.
	 * @param xml
	 * @return
	 */
	public synchronized static java.util.Date xmlToDate(final XMLGregorianCalendar xml) {

		if (xml == null) {
			return null;
		}

		final DecimalFormat yF = new DecimalFormat("0000");

		Date date = null;

		final StringBuffer sData = new StringBuffer();
		if (xml.getDay() < 10) {
			sData.append("0");
		}
		sData.append(xml.getDay());
		sData.append("/");
		if (xml.getMonth() < 10) {
			sData.append("0");
		}
		sData.append(xml.getMonth());
		sData.append("/");
		final int year = xml.getYear();
		sData.append(yF.format(year));
		if (xml.getHour() > 0) {
			sData.append(" ");
			if (xml.getHour() < 10) {
				sData.append("0");
			}
			sData.append(xml.getHour());
			sData.append(":");
			if (xml.getMinute() < 10) {
				sData.append("0");
			}
			sData.append(xml.getMinute());
			sData.append(":");
			if (xml.getSecond() < 10) {
				sData.append("0");
			}
			sData.append(xml.getSecond());

			// 'dd/MM/yyyy HH:mm:ss'
			date = parseDateHHmmss(sData.toString());
		} else {
			// 'dd/MM/yyyy'
			date = parseDate(sData.toString());
		}

		return date;
	}

	/**
	 * Devido a um bug do metodo toGregorianCalendar, da classe
	 * XMLGregorianCalendar, tive de implementar este.
	 * O bug estava jogando várias datas para 01/01/1970.
	 * @param xmlD elemento contendo a data
	 * @param xmlH elemento contendo a hora
	 * @return Timestamp com data e hora
	 */
	public synchronized static java.util.Date xmlToDateHour(final XMLGregorianCalendar xmlD, final XMLGregorianCalendar xmlH) {

		if (xmlD == null) {
			return null;
		}

		final DecimalFormat yF = new DecimalFormat("0000");

		java.util.Date date = null;

		final StringBuffer sData = new StringBuffer();
		if (xmlD.getDay() < 10) {
			sData.append("0");
		}
		sData.append(xmlD.getDay());
		sData.append("/");
		if (xmlD.getMonth() < 10) {
			sData.append("0");
		}
		sData.append(xmlD.getMonth());
		sData.append("/");
		final int year = xmlD.getYear();
		sData.append(yF.format(year));
		if (xmlH != null) {
			sData.append(" ");
			if (xmlH.getHour() < 10) {
				sData.append("0");
			}
			sData.append(xmlH.getHour());
			sData.append(":");
			if (xmlH.getMinute() < 10) {
				sData.append("0");
			}
			sData.append(xmlH.getMinute());
			sData.append(":");
			if (xmlH.getSecond() < 10) {
				sData.append("0");
			}
			sData.append(xmlH.getSecond());

			// 'dd/MM/yyyy HH:mm:ss'
			date = parseDateHHmmss(sData.toString());
		} else {
			// 'dd/MM/yyyy'
			date = parseDate(sData.toString());
		}

		return date;
	}


	/**
	 * Verifica se a data é maior que 01/01/1900 e menor que a data atual.
	 * Estas validações são necessárias para datas que são enviadas ao Uniben.
	 * @param data
	 * @return
	 */
	public synchronized static boolean validaData(final java.util.Date data) {
		boolean dtVal = true;

		if (data == null) {
			dtVal = false;
		} else {
			// validar data (não pode ser futura)
			if (data.after(CustomDate.getCurrentDate())) {
				dtVal = false;
			}
			// não pode ser anterior a 1/1/1900
			final java.util.Date limiteMin = CustomDate.parseDate("01/01/1900");
			if (data.before(limiteMin)) {
				dtVal = false;
			}
		}

		return dtVal;
	}

	public synchronized static boolean vencida(final java.util.Date data) {
		boolean dtVal = true;

		if (data != null && data.before(CustomDate.getCurrentDate())) {
			dtVal = true;
		}

		return dtVal;
	}

	public synchronized static final int getHora(final java.util.Date data) {
		final Calendar calendar = getCalendar(data);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

}
