package io.github.ziginsider.testrestapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs
import io.github.ziginsider.restapilib.db.entity.User
import io.github.ziginsider.restapilib.resttools.ProvideApi

class FromDbActivity : AppCompatActivity() {

    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_db)

        recyclerView = findViewById(R.id.recyclerView)

        val userFromDb = ProvideApi.getUsers(applicationContext)
        val favoriteGifsFromDb = ProvideApi.getFavoriteGifs(applicationContext)

        val userList: MutableList<UserWithGifsModel> = MutableList(userFromDb.size) {
            initUserList(userFromDb[it], favoriteGifsFromDb[it])
        }

        setUpRecyclerView(userList)
    }

    private fun initUserList(user: User, favoriteGifs: FavoriteGifs): UserWithGifsModel {
        return UserWithGifsModel(
            user.photoUrl,
            user.name,
            user.lastname,
            user.gender,
            user.age,
            user.birth,
            user.email,
            user.phone,
            favoriteGifs.gifs[0].images.fixed_width.url,
            favoriteGifs.gifs[1].images.fixed_width.url,
            favoriteGifs.gifs[2].images.fixed_width.url,
            favoriteGifs.gifs[3].images.fixed_width.url,
            favoriteGifs.gifs[4].images.fixed_width.url,
            favoriteGifs.gifs[5].images.fixed_width.url,
            favoriteGifs.gifs[6].images.fixed_width.url,
            favoriteGifs.gifs[7].images.fixed_width.url,
            favoriteGifs.gifs[8].images.fixed_width.url
        )
    }

    private fun setUpRecyclerView(list: List<UserWithGifsModel>) {
        recyclerViewAdapter = RecyclerViewAdapter(R.layout.db_item)
        recyclerViewAdapter?.submitList(list)
        val recyclerView = recyclerView ?: return
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
        }
    }
}
