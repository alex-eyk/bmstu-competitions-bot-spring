package com.alex.eyk.telegram.telegram.handler.perm

import com.alex.eyk.dictionary.keys.Replies
import com.alex.eyk.replies.dictionary.provider.DictionaryProvider

class NotifyStrategy(
    dictionary: DictionaryProvider
) : ReplyMessageStrategy(
    replyKey = Replies.PERMISSION_DENIED,
    dictionary
)
