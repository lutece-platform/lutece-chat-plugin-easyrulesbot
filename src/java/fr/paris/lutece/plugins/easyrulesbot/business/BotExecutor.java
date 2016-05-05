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
package fr.paris.lutece.plugins.easyrulesbot.business;

import fr.paris.lutece.plugins.easyrulesbot.business.rules.BotRule;
import fr.paris.lutece.plugins.easyrulesbot.service.response.ResponseFilter;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * BotExecutor
 */
public class BotExecutor implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Bot _bot;
    private BotRule _currentRule;
    private Map<String, String> _mapData = new HashMap<String, String>(  );
    private List<Post> _listPosts = new ArrayList<Post>(  );

    public BotExecutor( Bot bot )
    {
        _bot = bot;
    }

    /**
     * Gets the question
     * @return The question
     */
    public String getQuestion(  )
    {
        String strQuestion;

        if ( _currentRule != null )
        {
            strQuestion = _currentRule.getQuestion( _mapData );
        }
        else
        {
            // FIXME
            StringBuilder sb = new StringBuilder(  );
            sb.append( "Je n'ai plus de question. Voici les éléments collectés :<br><ul>" );

            for ( String strKey : _mapData.keySet(  ) )
            {
                sb.append( "<li>" ).append( strKey ).append( " : " ).append( _mapData.get( strKey ) ).append( "</li>" );
            }

            sb.append( "</ul>" );
            strQuestion = sb.toString(  );
        }

        return strQuestion;
    }

    /**
     * Process the response
     * @param strResponse The user response
     * @throws ResponseProcessingException
     */
    public void processResponse( String strResponse ) throws ResponseProcessingException
    {
        addUserPost( strResponse );

        String strResponseValue = processFilters( strResponse );

        if ( _currentRule != null )
        {
            strResponseValue = _currentRule.getResponseProcessor(  ).processResponse( strResponseValue );
            _mapData.put( _currentRule.getDataKey(  ), strResponseValue );

            String strResponseComment = _currentRule.getResponseComment( _mapData );

            if ( ( strResponseComment != null ) || strResponseComment.trim(  ).equals( "" ) )
            {
                addBotPost( strResponseComment );
            }
        }
    }

    /**
     * Process filters
     * @param strResponse The response
     * @return The filterd response
     * @throws ResponseProcessingException
     */
    private String processFilters( String strResponse )
        throws ResponseProcessingException
    {
        String strResponseValue = strResponse;

        for ( ResponseFilter filter : _bot.getResponseFilters(  ) )
        {
            strResponseValue = filter.filterResponse( strResponseValue );
        }

        return strResponseValue;
    }

    /**
     * Sets the current rule
     * @param rule The rule
     */
    public void setCurrentRule( BotRule rule )
    {
        _currentRule = rule;
    }

    /**
     * Executes rules.
     * NB : Should be thread safe.
     */
    public synchronized void fireRules(  )
    {
        _currentRule = null;

        RulesEngine engine = _bot.getRulesEngine(  );

        for ( Rule rule : engine.getRules(  ) )
        {
            if ( rule instanceof BotRule )
            {
                ( (BotRule) rule ).setExecutor( this );
            }
        }

        engine.fireRules(  );
    }

    /**
     * Add a post
     * @param post The post
     */
    public void addPost( Post post )
    {
        _listPosts.add( post );
    }

    /**
     * Add a post
     * @param strContent The content
     */
    public void addBotPost( String strContent )
    {
        addPost( strContent, Post.AUTHOR_BOT );
    }

    /**
     * Add a post
     * @param strContent The content
     */
    public void addUserPost( String strContent )
    {
        addPost( strContent, Post.AUTHOR_USER );
    }

    /**
     * Add a post
     * @param strContent The content
     * @param nAuthor The author
     */
    private void addPost( String strContent, int nAuthor )
    {
        Post post = new Post(  );
        post.setContent( strContent );
        post.setAuthor( nAuthor );
        _listPosts.add( post );
    }

    /**
     * The post list
     * @return
     */
    public List<Post> getPosts(  )
    {
        return _listPosts;
    }

    /**
     * Trace utils
     */
    public void traceData(  )
    {
        for ( String strKey : _mapData.keySet(  ) )
        {
            AppLogService.info( strKey + " : " + _mapData.get( strKey ) );
        }
    }

    /**
     * Gets all the data map
     * @return The data map
     */
    public Map<String, String> getDataMap(  )
    {
        return _mapData;
    }

    /**
     * Returns the bot name
     * @return the bot name
     */
    public String getBotName(  )
    {
        return _bot.getName(  );
    }
}
