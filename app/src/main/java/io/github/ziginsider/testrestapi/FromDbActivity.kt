package io.github.ziginsider.testrestapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs
import io.github.ziginsider.restapilib.db.entity.User
import io.github.ziginsider.restapilib.resttools.ProvideApi
import kotlinx.android.synthetic.main.db_item.*

class FromDbActivity : AppCompatActivity() {

    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_db)

        val userFromDb = ProvideApi.getUsers(applicationContext)
        val favoriteGifsFromDb = ProvideApi.getFavoriteGifs(applicationContext)

        val userList: MutableList<UserWithGifsModel> = MutableList(userFromDb.size) {
            initUserList(userFromDb[it], favoriteGifsFromDb[it])
        }

        Log.d("sdsd", "dssdf")

    }

    private fun initUserList(user: User, favoriteGifs: FavoriteGifs): UserWithGifsModel {
        return UserWithGifsModel(
            user.photoUrl,
            user.name,
            user.lastname,
            user.gender,
            user.email,
            user.phone,
            favoriteGifs.gifs[6].images.fixed_width.url,
            favoriteGifs.gifs[7].images.fixed_width.url,
            favoriteGifs.gifs[8].images.fixed_width.url
        )
    }
}
