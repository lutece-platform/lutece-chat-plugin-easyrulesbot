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
package fr.paris.lutece.plugins.easyrulesbot.service;

import fr.paris.lutece.plugins.easyrulesbot.business.Bot;
import fr.paris.lutece.plugins.easyrulesbot.business.BotExecutor;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * BotService
 */
public final class BotService
{
    private static List<Bot> _listBots;

    /** Private constructor */
    private BotService(  )
    {
    }

    /**
     * Provides a bot executor
     * @param strBotKey The bot key
     * @return The bot executor
     */
    public static synchronized BotExecutor getExecutor( String strBotKey )
    {
        Bot bot = getBot( strBotKey );

        if ( bot == null )
        {
            return null;
        }

        BotExecutor executor = new BotExecutor( bot );

        return executor;
    }

    /**
     * Get all bots
     * @return The bots list
     */
    public static List<Bot> getBots(  )
    {
        if ( _listBots == null )
        {
            _listBots = SpringContextService.getBeansOfType( Bot.class );
        }

        return _listBots;
    }

    /**
     * Get a bot by its key
     * @param strBotKey The bot key
     * @return The bot
     */
    public static Bot getBot( String strBotKey )
    {
        for ( Bot bot : getBots(  ) )
        {
            if ( bot.getKey(  ).equals( strBotKey ) )
            {
                return bot;
            }
        }

        return null;
    }
}
