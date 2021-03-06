/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.easyrulesbot.service.bot;

import fr.paris.lutece.plugins.chatbot.business.BotPost;
import fr.paris.lutece.plugins.chatbot.service.bot.AbstractChatBot;
import fr.paris.lutece.plugins.easyrulesbot.service.bot.rules.BotRule;
import fr.paris.lutece.plugins.easyrulesbot.service.response.filters.ResponseFilter;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;

/**
 * EasyRulesBot
 */
public class EasyRulesBot extends AbstractChatBot
{
    private static final String PROPERTY_LAST_MESSAGE = "easyrulesbot.bot.lastMessage";

    private static Map<String, BotExecutor> _mapBotExecutor = new HashMap<>( );
    private RulesEngine _engine;
    private List<ResponseFilter> _listResponseFilters;

    /**
     * {@inheritDoc }
     */
    @Override
    public List<BotPost> processUserMessage( String strMessage, String strConversationId, Locale locale )
    {
        List<BotPost> listBotPost;
        BotExecutor executor = _mapBotExecutor.get( strConversationId );
        if ( executor == null )
        {
            executor = new BotExecutor( this, locale );
            _mapBotExecutor.put( strConversationId, executor );
        }

        try
        {
            listBotPost = executor.processResponse( strMessage );
        }
        catch( ResponseProcessingException ex )
        {
            listBotPost = new ArrayList<>( );
            listBotPost.add( new BotPost( ex.getMessage( ), BotPost.CONTENT_TYPE_TEXT ) );
        }

        executor.fireRules( );

        String strQuestion = executor.getQuestion( );
        
        if( strQuestion != null )
        {
            listBotPost.add( new BotPost( strQuestion, BotPost.CONTENT_TYPE_TEXT ) );
        }

        executor.traceData( );

        return listBotPost;
    }

    /**
     * Returns the RulesEngine
     *
     * @return The RulesEngine
     */
    public RulesEngine getRulesEngine( )
    {
        return _engine;
    }

    /**
     * Sets the RulesEngine
     *
     * @param rulesEngine
     *            The RulesEngine
     */
    public void setRulesEngine( RulesEngine rulesEngine )
    {
        _engine = rulesEngine;
    }

    /**
     * Set the list of response filters
     *
     * @param list
     *            The list
     *
     */
    public void setListResponseFilters( List<ResponseFilter> list )
    {
        _listResponseFilters = list;
    }

    /**
     * The list of response filters
     *
     * @return The list
     */
    public List<ResponseFilter> getResponseFilters( )
    {
        return _listResponseFilters;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void reset( String strConversationId )
    {
        super.reset( strConversationId );
        
        _mapBotExecutor.remove( strConversationId );
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public String toString()
    {
        StringBuilder sbOutput = new StringBuilder( "\nEASYRULE BOT\n");
        sbOutput.append( "\n key : " ).append( getKey() );
        sbOutput.append( "\n name : " ).append( getName( LocaleService.getDefault()) );
        sbOutput.append( "\n description : " ).append( getDescription( LocaleService.getDefault() ) );
        sbOutput.append( "\n avatar URL : " ).append( getAvatarUrl() );
        sbOutput.append( "\n welcome message : " ).append( getWelcomeMessage() );
        sbOutput.append( "\n Rules : " );
        for( Rule rule : getRulesEngine().getRules() )
        {
            BotRule br = (BotRule) rule;
            sbOutput.append( br );
        }
        return sbOutput.toString();
    }

    
}
