/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.easyrulesbot.business.rules;

import fr.paris.lutece.plugins.easyrulesbot.business.BotExecutor;
import fr.paris.lutece.plugins.easyrulesbot.business.rules.conditions.Condition;
import fr.paris.lutece.plugins.easyrulesbot.service.response.ResponseProcessor;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.easyrules.api.Rule;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * BotRule
 */
public class BotRule implements Rule, Comparable, Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private String _strName;
    private String _strDescription;
    private String _strQuestionTemplate;
    private String _strResponseCommentTemplate;
    private String _strDescriptionI18nKey;
    private String _strQuestionTemplateI18nKey;
    private String _strResponseCommentTemplateI18nKey;
    private String _strDataKey;
    private int _nPriority;
    private BotExecutor _executor;
    private ResponseProcessor _responseProcessor;
    private List<Condition> _listConditions;

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName(  )
    {
        return _strName;
    }

    /**
     * Sets the Name
     * @param strName The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * @param strDescription The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the DescriptionI18nKey
     * @return The DescriptionI18nKey
     */
    public String getDescriptionI18nKey(  )
    {
        return _strDescriptionI18nKey;
    }

    /**
     * Sets the DescriptionI18nKey
     * @param strDescriptionI18nKey The DescriptionI18nKey
     */
    public void setDescriptionI18nKey( String strDescriptionI18nKey )
    {
        _strDescriptionI18nKey = strDescriptionI18nKey;
    }

    /**
     * Returns the QuestionTemplateI18nKey
     * @return The QuestionTemplateI18nKey
     */
    public String getQuestionTemplateI18nKey(  )
    {
        return _strQuestionTemplateI18nKey;
    }

    /**
     * Sets the QuestionTemplateI18nKey
     * @param strQuestionTemplateI18nKey The QuestionTemplateI18nKey
     */
    public void setQuestionTemplateI18nKey( String strQuestionTemplateI18nKey )
    {
        _strQuestionTemplateI18nKey = strQuestionTemplateI18nKey;
    }

    /**
     * Returns the ResponseCommentTemplateI18nKey
     * @return The ResponseCommentTemplateI18nKey
     */
    public String getResponseCommentTemplateI18nKey(  )
    {
        return _strResponseCommentTemplateI18nKey;
    }

    /**
     * Sets the ResponseCommentTemplateI18nKey
     * @param strResponseCommentTemplateI18nKey The ResponseCommentTemplateI18nKey
     */
    public void setResponseCommentTemplateI18nKey( String strResponseCommentTemplateI18nKey )
    {
        _strResponseCommentTemplateI18nKey = strResponseCommentTemplateI18nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getPriority(  )
    {
        return _nPriority;
    }

    /**
     * Sets the Priority
     * @param nPriority The Priority
     */
    public void setPriority( int nPriority )
    {
        _nPriority = nPriority;
    }

    /**
     * Returns the DataKey
     * @return The DataKey
     */
    public String getDataKey(  )
    {
        return _strDataKey;
    }

    /**
     * Sets the DataKey
     * @param strDataKey The DataKey
     */
    public void setDataKey( String strDataKey )
    {
        _strDataKey = strDataKey;
    }

    /**
     * Returns the Question
     * @param mapData The map of data
     * @param locale The locale
     * @return The Question
     */
    public String getQuestion( Map<String, String> mapData, Locale locale )
    {
        String strQuestion;

        if ( _strQuestionTemplateI18nKey != null )
        {
            strQuestion = I18nService.getLocalizedString( _strQuestionTemplateI18nKey, locale );
        }
        else
        {
            strQuestion = _strQuestionTemplate;
        }

        return fillTemplate( strQuestion, mapData );
    }

    /**
     * Sets the Question
     * @param strQuestion The Question
     */
    public void setQuestionTemplate( String strQuestion )
    {
        _strQuestionTemplate = strQuestion;
    }

    /**
     * Define the current executor using the rule
     * @param executor The executor
     */
    public void setExecutor( BotExecutor executor )
    {
        _executor = executor;
    }

    /**
     * Sets the response processor
     * @param responseProcessor the response processor
     */
    public void setResponseProcessor( ResponseProcessor responseProcessor )
    {
        _responseProcessor = responseProcessor;
    }

    /**
     * Gets the response processor
     * @return the response processor
     */
    public ResponseProcessor getResponseProcessor(  )
    {
        return _responseProcessor;
    }

    /**
     * Sets the conditions list
     * @param listConditions The list of condition
     */
    public void setListConditions( List<Condition> listConditions )
    {
        _listConditions = listConditions;
    }

    /**
     * Define the response comment template
     * @param strResponseCommentTemplate The template
     */
    public void setResponseCommentTemplate( String strResponseCommentTemplate )
    {
        _strResponseCommentTemplate = strResponseCommentTemplate;
    }

    /**
     * Provides a comment after the user's response
     * @param mapData The data
     * @param locale The locale
     * @return The response
     */
    public String getResponseComment( Map<String, String> mapData, Locale locale )
    {
        String strTemplate;

        if ( _strResponseCommentTemplateI18nKey != null )
        {
            strTemplate = I18nService.getLocalizedString( _strResponseCommentTemplateI18nKey, locale );
        }
        else
        {
            strTemplate = _strResponseCommentTemplate;
        }

        if ( strTemplate != null )
        {
            return fillTemplate( strTemplate, mapData );
        }

        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean evaluate(  )
    {
        Map<String, String> mapData = _executor.getDataMap(  );

        for ( Condition condition : _listConditions )
        {
            if ( !condition.evaluate( mapData, _strDataKey ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute(  ) throws Exception
    {
        _executor.setCurrentRule( this );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int compareTo( Object object )
    {
        Rule rule = (Rule) object;

        return getPriority(  ) - rule.getPriority(  );
    }

    /**
     * Fill a template with data contained in a model map
     * @param strTemplateString The template
     * @param model The model
     * @return The filled string
     */
    private String fillTemplate( String strTemplateString, Map<String, String> model )
    {
        if ( !strTemplateString.contains( "{" ) && !strTemplateString.contains( "<#" ) )
        {
            // No marker and no freemarker tag so nothing to transform
            return strTemplateString;
        }

        String strTemplate = strTemplateString.replace( "{", "${" ); // $ removed in context file
        HtmlTemplate template = AppTemplateService.getTemplateFromStringFtl( strTemplate, LocaleService.getDefault(  ),
                model );

        return template.getHtml(  );
    }
}
