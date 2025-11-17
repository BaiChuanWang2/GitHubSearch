package com.example.githubsearch.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubsearch.data.serializable.User
import com.example.githubsearch.presentation.navigation.AppDestinations
import com.example.githubsearch.presentation.theme.darkColors
import com.example.githubsearch.presentation.viewmodel.GitHubUserViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AppView(
    viewModel: GitHubUserViewModel = hiltViewModel()
) {
    val users by viewModel.users.collectAsState()
    val favoriteUsers by viewModel.favoriteUsers.collectAsState()

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val navController = rememberNavController()

    MaterialTheme(
        colorScheme = darkColors
    ) {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = it.label
                            ) },
                        label = {
                            Text(
                                text = it.label,
                                color = Color.White
                            ) },
                        selected = it == currentDestination,
                        onClick = {
                            currentDestination = it
                            navController.navigate(it.label)
                        })
                }
            },
            navigationSuiteColors = NavigationSuiteDefaults.colors(
                navigationBarContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                navigationRailContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                navigationDrawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = AppDestinations.HOME.label,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(
                        AppDestinations.HOME.label
                    ) {
                        HomeScreen(
                            viewModel = viewModel,
                            users = users,
                            favoriteUsers = favoriteUsers
                        )
                    }
                    composable(
                        AppDestinations.FAVORITES.label
                    ) {
                        FavoritesScreen(
                            favoriteUsers = favoriteUsers
                        )
                    }
                    composable(
                        AppDestinations.Setting.label
                    ) {
                        SettingScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: GitHubUserViewModel,
    users: List<User>,
    favoriteUsers: List<User>
) {
    var textSearch by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textSearch,
                onValueChange = { textSearch = it },
                label = { Text("ユーザー名",) },
                placeholder = {
                    Text(
                        text = "ユーザー名を入力してください。",
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    ) },
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )

            Button(
                onClick = {
                    viewModel.searchUsers(textSearch)
                }
            ) {
                Text("検索")
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(start = 0.dp, top = 16.dp, bottom = 16.dp, end = 0.dp)
        ) {
            items(
                users.size
            ) { i ->
                val userName = users[i].login
                val avatarUrl = users[i].avatar_url
                val user = users[i]
                val isFavorite = favoriteUsers.any { it.id == user.id }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            color = Color(0xFF1E1E1E),
                            shape = RoundedCornerShape(4.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null)

                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = userName)

                    IconButton(
                        onClick = {
                            viewModel.toggleFavorite(user)
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}

@Composable
fun FavoritesScreen(
    favoriteUsers: List<User>
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(
            favoriteUsers.size
        ) { i ->
            val userName = favoriteUsers[i].login
            val avatarUrl = favoriteUsers[i].avatar_url

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        color = Color(0xFF1E1E1E),
                        shape = RoundedCornerShape(4.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null)

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = userName)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )
        }
    }
}

@Composable
fun SettingScreen() {

}