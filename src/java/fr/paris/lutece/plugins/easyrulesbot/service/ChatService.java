/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import fr.paris.lutece.plugins.easyrulesbot.business.BotExecutor;
import fr.paris.lutece.plugins.easyrulesbot.business.ChatData;
import fr.paris.lutece.plugins.easyrulesbot.business.Conversation;
import fr.paris.lutece.plugins.easyrulesbot.business.Post;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * ChatService
 */
public class ChatService 
{
    private static Map<String, ChatData> _mapConversations = new HashMap<>();


    public static void processMessage( HttpServletRequest request , String strUserId , String strMessage, String strBotKey, Locale locale )
    {
        ChatData data = _mapConversations.get( strUserId );
        if( data == null )
        {
            data = createNewConversation( strBotKey );
            _mapConversations.put( strUserId , data );
        }
        BotExecutor executor = data.getExecutor();
        data.addUserPost( strMessage );
        String strBotComment = null;
        try
        {
            strBotComment = executor.processUserMessage( strMessage );
        }
        catch( ResponseProcessingException ex )
        {
            data.addBotPost( ex.getMessage() );
        }
        if( strBotComment != null )
        {
            
        }
        executor.fireRules( );

        String strBotMessage = executor.getBotMessage( request );
        data.addBotPost( strBotMessage );

        executor.traceData( );
        
    }
    
    public static List<Post> getConversation( String strUserId )
    {
        ChatData data = _mapConversations.get( strUserId );
        if( data == null )
        {
        }
        return data.getPosts();
    }

    private static ChatData createNewConversation( String strBotKey )
    {
        ChatData data = new ChatData( strBotKey );
        return data;
    }
}
