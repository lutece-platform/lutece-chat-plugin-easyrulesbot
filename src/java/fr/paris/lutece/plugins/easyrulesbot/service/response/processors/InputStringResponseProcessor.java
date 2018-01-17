/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.easyrulesbot.service.response.processors;

import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.InvalidResponseException;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InputStringResponseProcessor
 */
public class InputStringResponseProcessor extends AbstractProcessor implements ResponseProcessor
{
    private static final String PATTERN_EMAIL_NAME = "email";
    private static final Pattern PATTERN_EMAIL_VALUE = Pattern.compile( "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE );
    private String _strStandardPattern;
    private String _strCustomPattern;
    private String _strPatternErrorMessage;
    private String _strPatternErrorMessageI18nKey;

    /**
     * Returns the StandardPattern
     * 
     * @return The StandardPattern
     */
    public String getStandardPattern( )
    {
        return _strStandardPattern;
    }

    /**
     * Sets the StandardPattern
     * 
     * @param strStandardPattern
     *            The StandardPattern
     */
    public void setStandardPattern( String strStandardPattern )
    {
        _strStandardPattern = strStandardPattern;
    }

    /**
     * Returns the CustomPattern
     * 
     * @return The CustomPattern
     */
    public String getCustomPattern( )
    {
        return _strCustomPattern;
    }

    /**
     * Sets the CustomPattern
     * 
     * @param strCustomPattern
     *            The CustomPattern
     */
    public void setCustomPattern( String strCustomPattern )
    {
        _strCustomPattern = strCustomPattern;
    }

    /**
     * Returns the PatternErrorMessage
     * 
     * @return The PatternErrorMessage
     */
    public String getPatternErrorMessage( )
    {
        return _strPatternErrorMessage;
    }

    /**
     * Sets the PatternErrorMessage
     * 
     * @param strPatternErrorMessage
     *            The PatternErrorMessage
     */
    public void setPatternErrorMessage( String strPatternErrorMessage )
    {
        _strPatternErrorMessage = strPatternErrorMessage;
    }

    /**
     * Returns the PatternErrorMessageI18nKey
     * 
     * @return The PatternErrorMessageI18nKey
     */
    public String getPatternErrorMessageI18nKey( )
    {
        return _strPatternErrorMessageI18nKey;
    }

    /**
     * Sets the PatternErrorMessageI18nKey
     * 
     * @param strPatternErrorMessageI18nKey
     *            The PatternErrorMessageI18nKey
     */
    public void setPatternErrorMessageI18nKey( String strPatternErrorMessageI18nKey )
    {
        _strPatternErrorMessageI18nKey = strPatternErrorMessageI18nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String processResponse( String strResponse, Locale locale, Map mapData ) throws InvalidResponseException
    {
        if ( _strStandardPattern != null )
        {
            checkStandardPattern( strResponse, locale );
        }

        if ( _strCustomPattern != null )
        {
            checkCustomPattern( strResponse, locale );
        }

        return strResponse;
    }

    /**
     * Check if the response matches a standard pattern
     * 
     * @param strResponse
     *            The response
     * @param locale
     *            The locale
     * @throws InvalidResponseException
     *             if doesn't match the pattern
     */
    private void checkStandardPattern( String strResponse, Locale locale ) throws InvalidResponseException
    {
        if ( _strStandardPattern.equalsIgnoreCase( PATTERN_EMAIL_NAME ) )
        {
            Matcher matcher = PATTERN_EMAIL_VALUE.matcher( strResponse );

            if ( !matcher.find( ) )
            {
                processInvalidResponse( locale );
            }
        }
    }

    /**
     * Check if the response matches the custom pattern
     * 
     * @param strResponse
     *            The response
     * @param locale
     *            The locale
     * @throws InvalidResponseException
     *             if doesn't match the pattern
     */
    private void checkCustomPattern( String strResponse, Locale locale ) throws InvalidResponseException
    {
        Pattern pattern = Pattern.compile( _strCustomPattern, Pattern.CASE_INSENSITIVE );
        Matcher matcher = pattern.matcher( strResponse );

        if ( !matcher.find( ) )
        {
            processInvalidResponse( locale );
        }
    }

    /**
     * Process an invalid response
     * 
     * @param locale
     *            The locale
     * @throws InvalidResponseException
     *             with the
     */
    private void processInvalidResponse( Locale locale ) throws InvalidResponseException
    {
        String strMessage;

        if ( _strPatternErrorMessageI18nKey != null )
        {
            strMessage = I18nService.getLocalizedString( _strPatternErrorMessageI18nKey, locale );
        }
        else
        {
            strMessage = _strPatternErrorMessage;
        }

        throw new InvalidResponseException( strMessage );
    }
}
