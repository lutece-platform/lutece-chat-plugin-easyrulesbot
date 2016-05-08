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
package fr.paris.lutece.plugins.easyrulesbot.web;

import fr.paris.lutece.plugins.easyrulesbot.business.Bot;
import fr.paris.lutece.plugins.easyrulesbot.business.BotDescription;
import fr.paris.lutece.plugins.easyrulesbot.business.BotExecutor;
import fr.paris.lutece.plugins.easyrulesbot.service.BotService;
import static fr.paris.lutece.plugins.easyrulesbot.service.BotService.getBots;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides a simple implementation of an XPage
 */
@Controller( xpageName = "bot", pageTitleI18nKey = "easyrulesbot.xpage.bot.pageTitle", pagePathI18nKey = "easyrulesbot.xpage.bot.pagePathLabel" )
public class EasyRulesBotApp extends MVCApplication
{
    private static final String TEMPLATE_BOT = "/skin/plugins/easyrulesbot/bot.html";
    private static final String TEMPLATE_BOTS_LIST = "/skin/plugins/easyrulesbot/bots_list.html";
    private static final String MARK_BOTS_LIST = "bots_list";
    private static final String MARK_POSTS_LIST = "posts_list";
    private static final String PARAMETER_BOT = "bot";
    private static final String PARAMETER_RESPONSE = "response";
    private static final String PARAMETER_LANGUAGE = "lang";
    private static final String VIEW_LIST = "list";
    private static final String VIEW_BOT = "bot";
    private static final String ACTION_RESPONSE = "response";
    private static final String RESET = "reset";
    private static final String URL_BOT = "jsp/site/Portal.jsp?page=bot&view=bot";
    private static final long serialVersionUID = 1L;
    private BotExecutor _executor;

    /**
     * Returns the content of the list of bots page
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_LIST, defaultView = true )
    public XPage viewList( HttpServletRequest request )
    {
        List<BotDescription> listBots = getBotsDescription(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_BOTS_LIST, listBots );

        return getXPage( TEMPLATE_BOTS_LIST, request.getLocale(  ), model );
    }

    /**
     * Returns the content of the bot page.
     * @param request The HTTP request
     * @return The page
     */
    @View( VIEW_BOT )
    public XPage viewBot( HttpServletRequest request )
    {
        if ( _executor == null )
        {
            String strBotKey = request.getParameter( PARAMETER_BOT );

            if ( strBotKey != null )
            {
                _executor = BotService.getExecutor( strBotKey );
                _executor.setLocale( getBotLocale( request ) );

                if ( _executor == null )
                {
                    return redirectView( request, VIEW_LIST );
                }
            }
            else
            {
                return redirectView( request, VIEW_LIST );
            }
        }

        _executor.fireRules(  );

        String strQuestion = _executor.getQuestion(  );
        _executor.addBotPost( strQuestion );

        _executor.traceData(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_POSTS_LIST, _executor.getPosts(  ) );

        XPage xpage = getXPage( TEMPLATE_BOT, request.getLocale(  ), model );
        xpage.setTitle( _executor.getBotName(  ) );
        xpage.setPathLabel( _executor.getBotName(  ) );

        return xpage;
    }

    /**
     * Process the response
     * @param request The HTTP request
     * @return The redirected page
     */
    @Action( ACTION_RESPONSE )
    public XPage doProcessResponse( HttpServletRequest request )
    {
        String strResponse = request.getParameter( PARAMETER_RESPONSE );

        if ( RESET.equals( strResponse ) )
        {
            _executor = null;

            return redirectView( request, VIEW_BOT );
        }

        try
        {
            _executor.processResponse( strResponse );
        }
        catch ( ResponseProcessingException ex )
        {
            _executor.addBotPost( ex.getMessage(  ) );
        }

        return redirectView( request, VIEW_BOT );
    }

    /**
     * Get request information for the bot language
     * @param request The request
     * @return The locale
     */
    private Locale getBotLocale( HttpServletRequest request )
    {
        String strLanguage = request.getParameter( PARAMETER_LANGUAGE );

        if ( strLanguage != null )
        {
            return new Locale( strLanguage );
        }

        return LocaleService.getDefault(  );
    }

    /**
     * Gets the list of bots
     * @return The list of bots
     */
    private List<BotDescription> getBotsDescription(  )
    {
        List<BotDescription> list = new ArrayList<BotDescription>(  );

        for ( Bot bot : getBots(  ) )
        {
            List<String> listLanguages = bot.getAvailableLanguages(  );

            if ( listLanguages != null )
            {
                for ( String strLanguage : listLanguages )
                {
                    BotDescription botDescription = new BotDescription(  );
                    Locale locale = new Locale( strLanguage );
                    botDescription.setName( bot.getName( locale ) );
                    botDescription.setDescription( bot.getDescription( locale ) );
                    botDescription.setLanguage( locale.getDisplayLanguage(  ) );

                    UrlItem url = new UrlItem( URL_BOT );
                    url.addParameter( PARAMETER_BOT, bot.getKey(  ) );
                    url.addParameter( PARAMETER_LANGUAGE, strLanguage );
                    botDescription.setUrl( url.getUrl(  ) );
                    list.add( botDescription );
                }
            }
            else
            {
                BotDescription botDescription = new BotDescription(  );
                Locale locale = LocaleService.getDefault(  );
                botDescription.setName( bot.getName( locale ) );
                botDescription.setDescription( bot.getDescription( locale ) );
                botDescription.setLanguage( locale.getDisplayLanguage(  ) );
                list.add( botDescription );
            }
        }

        return list;
    }
}
