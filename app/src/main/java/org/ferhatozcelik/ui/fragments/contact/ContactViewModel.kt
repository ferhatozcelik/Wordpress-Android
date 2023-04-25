package org.ferhatozcelik.ui.fragments.contact

import android.content.Context
import androidx.lifecycle.ViewModel
import org.ferhatozcelik.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-01
 */

@HiltViewModel
class ContactViewModel @Inject constructor(private val articleRepository: ArticleRepository, @ApplicationContext private val context: Context) : ViewModel() {


}