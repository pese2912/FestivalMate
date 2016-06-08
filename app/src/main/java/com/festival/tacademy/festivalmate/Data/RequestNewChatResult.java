package com.festival.tacademy.festivalmate.Data;

import com.festival.tacademy.festivalmate.MateTalk.Receive;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tacademy on 2016-06-08.
 */
public class RequestNewChatResult  implements Serializable {
    public int success;
    public String message;
    public List<Receive> result;

}
