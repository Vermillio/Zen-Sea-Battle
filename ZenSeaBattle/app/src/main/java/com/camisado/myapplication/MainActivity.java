package com.camisado.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.graphics.Rect;
import android.widget.EditText;

import java.util.Arrays;
import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;


public class MainActivity extends AppCompatActivity {
    int FIELD_SIZE=10;
    ApplyTheme applyTheme;
    Player player_1, player_2;
    private int currentLocation;
    private boolean SinglePlayer;
    private GestureDetector gD;
    private ShipsInput shipsInput;
    private GameHandler gameHandler;


    private String str(int n) {
        return Integer.toString(n) + " ";
    }

    private int min(int x, int y) {
        return Math.min(x, y);
    }

    private int max(int x, int y) {
        return Math.max(x, y);
    }

    private float min(float x, float y) {
        return Math.min(x, y);
    }

    private float max(float x, float y) {
        return Math.max(x, y);
    }

    int getViewID(String s) {
        try
        {
            return R.id.class.getDeclaredField(s).getInt(0);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        };
        return 0;
    };

    int getDrawableID(String s){
        try
        {
            return R.drawable.class.getDeclaredField(s).getInt(0);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        };
        return 0;
    }

    class ApplyTheme {
        int colors[][];
        int theme;
        int defaultThemeValue;
        int prevLocation;

        int buttonSize=min(getDisplayWidth(), getDisplayWidth())/8;

        ApplyTheme() {
            defaultThemeValue = 0;
            readTheme();
            initColors();
        };

        public void readTheme() {
            SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
            theme = sp.getInt(getString(R.string.saved_theme_key), defaultThemeValue);
        };

        public void saveTheme() {
            SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(getString(R.string.saved_theme_key), theme);
            editor.apply();
        }

        private void initColors() {
            //0 - background color1, 1 - background color2, 2 - text color, 3 - light text color, 4 - buttons color, 5 - buttons text color, 6 - cells 1 color, 7 - cells 2 color, 8+ - ships colors
            colors = new int[17][11];
            colors[0][0] = R.color.FloralWhite;
            colors[0][1] = R.color.FloralWhite;
            colors[0][2] = R.color.WarmBlack;
            colors[0][3] = R.color.DarkButton;
            colors[0][4] = R.color.DarkButton;
            colors[0][5] = R.color.FloralWhite;
            colors[0][6] = R.color.DarkButton;
            colors[0][7] = R.color.WarmBlack;
            colors[0][8] = R.color.CadetBlue;
            colors[0][9] = R.color.MyrtleGreen;
            colors[0][10] = R.color.LavenderGray;

            colors[1][0] = R.color.Ebony;
            colors[1][1] = R.color.Alabaster;
            colors[1][2] = R.color.RomanSilver;
            colors[1][3] = R.color.DarkButton;
            colors[1][4] = R.color.DarkButton;
            colors[1][5] = R.color.Ebony;
            colors[1][6] = R.color.DarkButton;
            colors[1][7] = R.color.PewterBlue2;
            colors[1][8] = R.color.TeaRose;
            colors[1][9] = R.color.KeyLime;
            colors[1][10] = R.color.PeachOrange;

            colors[2][0] = R.color.FloralWhite;
            colors[2][1] = R.color.Platinum;
            colors[2][2] = R.color.Taupe;
            colors[2][3] = R.color.DarkButton;
            colors[2][4] = R.color.DarkButton;
            colors[2][5] = R.color.FloralWhite;
            colors[2][6] = R.color.DarkButton;
            colors[2][7] = R.color.Taupe;
            colors[2][8] = R.color.PowderBlue;
            colors[2][9] = R.color.PinkSafron; //TODO: make orange softer
            colors[2][10] = R.color.Thistle;

            colors[3][0] = R.color.LanguidLavender;
            colors[3][1] = R.color.QuickSilver;
            colors[3][2] = R.color.EerieBlack;
            colors[3][3] = R.color.DarkButton;
            colors[3][4] = R.color.DarkButton;
            colors[3][5] = R.color.LanguidLavender;
            colors[3][6] = R.color.DarkButton;
            colors[3][7] = R.color.QuickSilver;
            colors[3][8] = R.color.EerieBlack;
            colors[3][9] = R.color.YankeesBlue;
            colors[3][10] = R.color.Charcoal;

            colors[4][0] = R.color.OuterSpace;
            colors[4][1] = R.color.BlackCoral;
            colors[4][2] = R.color.QuickSilver;
            colors[4][3] = R.color.DarkButton;
            colors[4][4] = R.color.DarkButton;
            colors[4][5] = R.color.OuterSpace;
            colors[4][6] = R.color.BlackCoral;
            colors[4][7] = R.color.QuickSilver;
            colors[4][8] = R.color.Melon;
            colors[4][9] = R.color.Soap;
            colors[4][10] = R.color.PaleTurquioise;

            colors[5][0] = R.color.BlackCoral;
            colors[5][1] = R.color.Cadet;
            colors[5][2] = R.color.OuterSpace;
            colors[5][3] = R.color.DarkButton;
            colors[5][4] = R.color.DarkButton;
            colors[5][5] = R.color.BlackCoral;
            colors[5][6] = R.color.DarkButton;
            colors[5][7] = R.color.OuterSpace;
            colors[5][8] = R.color.SunsetOrange;
            colors[5][9] = R.color.Melon;
            colors[5][10] = R.color.LightCoral;

//            colors[6][0]=R.color.Alabaster2;
//            colors[6][1]=R.color.Alabaster;
//            colors[6][2]=R.color.QuickSilver;
//            colors[6][3]=R.color.Alabaster;
//            colors[6][4]=R.color.QuickSilver;
//            colors[6][5]=R.color.Alabaster2;
//            colors[6][6]=R.color.Alabaster;
//            colors[6][7]=R.color.QuickSilver;
//            colors[6][8]=R.color.Khaki;
//            colors[6][9]=R.color.RoseTaupe;
//            colors[6][10]=R.color.DavysGray;
//
//            colors[6][0]=R.color.GunMetal;
//            colors[6][1]=R.color.OuterSpace;
//            colors[6][2]=R.color.VeryPaleYellow;
//            colors[6][3]=R.color.VeryPaleYellow;
//            colors[6][4]=R.color.VeryPaleYellow;
//            colors[6][5]=R.color.GunMetal;
//            colors[6][6]=R.color.VeryPaleYellow;
//            colors[6][7]=R.color.StormCloud;
//            colors[6][8]=R.color.YellowGreen;
//            colors[6][9]=R.color.PalmLeaf;
//            colors[6][10]=R.color.Pistaccio;


            colors[6][0] = R.color.Linen;
            colors[6][1] = R.color.Linen;
            colors[6][2] = R.color.DarkPurple;
            colors[6][3] = R.color.DarkPurple;
            colors[6][4] = R.color.DarkPurple;
            colors[6][5] = R.color.Linen;
            colors[6][6] = R.color.DarkButton;
            colors[6][7] = R.color.DarkPurple;
            colors[6][8] = R.color.PastelRed2;
            colors[6][9] = R.color.BrightTurquoise;
            colors[6][10] = R.color.DeepTaupe;

            colors[7][0]=R.color.FloralWhite;
            colors[7][1]=R.color.FloralWhite;
            colors[7][2]=R.color.JapaneseIndigo2;
            colors[7][3]=R.color.JapaneseIndigo2;
            colors[7][4]=R.color.JapaneseIndigo2;
            colors[7][5]=R.color.FloralWhite;
            colors[7][6]=R.color.DarkButton;
            colors[7][7]=R.color.JapaneseIndigo2;
            colors[7][8]=R.color.LightCoral;
            colors[7][9]=R.color.MontantRed;
            colors[7][10]=R.color.Prune;

            colors[8][0] = R.color.FloralWhite;
            colors[8][1] = R.color.FloralWhite;
            colors[8][2] = R.color.QuickSilver;
            colors[8][3] = R.color.Gainsboro;
            colors[8][4] = R.color.Gainsboro;
            colors[8][5] = R.color.FloralWhite;
            colors[8][6] = R.color.Gainsboro;
            colors[8][7] = R.color.QuickSilver;
            colors[8][8] = R.color.PastelRed;
            colors[8][9] = R.color.TealBlue;
            colors[8][10] = R.color.LightSeaGreen;

            colors[9][0] = R.color.DeepSpaceSparkle;
            colors[9][1] = R.color.LightGoldenrodYellow;
            colors[9][2] = R.color.WeldonBlue;
            colors[9][3] = R.color.DarkButton;
            colors[9][4] = R.color.DarkButton;
            colors[9][5] = R.color.DeepSpaceSparkle;
            colors[9][6] = R.color.DarkButton;
            colors[9][7] = R.color.WeldonBlue;
            colors[9][8] = R.color.LightGoldenrodYellow;
            colors[9][9] = R.color.LightGoldenrodYellow;
            colors[9][10] = R.color.LightGoldenrodYellow;

            colors[10][0] = R.color.OuterSpace2;
            colors[10][1] = R.color.YankeesBlue2;
            colors[10][2] = R.color.YankeesBlue2;
            colors[10][3] = R.color.DarkButton;
            colors[10][4] = R.color.DarkButton;
            colors[10][5] = R.color.YankeesBlue2;
            colors[10][6] = R.color.DarkButton;
            colors[10][7] = R.color.YankeesBlue2;
            colors[10][8] = R.color.TeaGreen2;
            colors[10][9] = R.color.ColumbiaBlue2;
            colors[10][10] = R.color.PaleCerulean;

            colors[11][0] = R.color.FloralWhite;
            colors[11][1] = R.color.Jonquil;
            colors[11][2] = R.color.GunMetal2;
            colors[11][3] = R.color.DarkButton;
            colors[11][4] = R.color.GunMetal2;
            colors[11][5] = R.color.FloralWhite;
            colors[11][6] = R.color.DarkButton;
            colors[11][7] = R.color.Jonquil;
            colors[11][8] = R.color.Taupe;
            colors[11][9] = R.color.GunMetal2;
            colors[11][10] = R.color.Onyx2;

            colors[12][0] = R.color.Cream;
            colors[12][1] = R.color.KeyLime;
            colors[12][2] = R.color.Onyx;
            colors[12][3] = R.color.Onyx;
            colors[12][4] = R.color.Onyx;
            colors[12][5] = R.color.Cream;
            colors[12][6] = R.color.DarkButton;
            colors[12][7] = R.color.Onyx;
            colors[12][8] = R.color.Coral;
            colors[12][9] = R.color.NavajoWhite;
            colors[12][10] = R.color.KeyLime;

            colors[13][0] = R.color.CosmicLatte;
            colors[13][1] = R.color.CosmicLatte;
            colors[13][2] = R.color.Onyx2;
            colors[13][3] = R.color.Onyx2;
            colors[13][4] = R.color.Onyx2;
            colors[13][5] = R.color.CosmicLatte;
            colors[13][6] = R.color.DarkButton;
            colors[13][7] = R.color.Onyx2;
            colors[13][8] = R.color.AeroBlue;
            colors[13][9] = R.color.PewterBlue3;
            colors[13][10] = R.color.OldLavender;

            colors[14][0] = R.color.Ebony;
            colors[14][1] = R.color.Ebony;
            colors[14][2] = R.color.Beige2;
            colors[14][3] = R.color.Beige2;
            colors[14][4] = R.color.Beige2;
            colors[14][5] = R.color.Ebony;
            colors[14][6] = R.color.DarkButton;
            colors[14][7] = R.color.Beige2;
            colors[14][8] = R.color.PastelYellow;
            colors[14][9] = R.color.BurlyWood;
            colors[14][10] = R.color.PaleCerulean2;

            colors[15][0] = R.color.FloralWhite;
            colors[15][1] = R.color.FloralWhite;
            colors[15][2] = R.color.Taupe;
            colors[15][3] = R.color.DarkButton;
            colors[15][4] = R.color.DarkButton;
            colors[15][5] = R.color.FloralWhite;
            colors[15][6] = R.color.DarkButton;
            colors[15][7] = R.color.Taupe;
            colors[15][8] = R.color.SatinSheenGold;
            colors[15][9] = R.color.PalmLeaf2;
            colors[15][10] = R.color.YankeesBlue3;

            colors[16][0] = R.color.FloralWhite;
            colors[16][1] = R.color.FloralWhite;
            colors[16][2] = R.color.Taupe;
            colors[16][3] = R.color.DarkButton;
            colors[16][4] = R.color.DarkButton;
            colors[16][5] = R.color.FloralWhite;
            colors[16][6] = R.color.DarkButton;
            colors[16][7] = R.color.Taupe;
            colors[16][8] = R.color.SatinSheenGold;
            colors[16][9] = R.color.PalmLeaf2;
            colors[16][10] = R.color.YankeesBlue3;
        }


        public Drawable updateColor(Drawable drawable, int color) {
            //drawable.clearColorFilter();
            drawable.setColorFilter(getColor(colors[theme][color]), PorterDuff.Mode.MULTIPLY);
            return drawable;
        }

        public void nextTheme() {
            ++theme;
            theme %= colors.length;
            saveTheme();
        }



        public void all(int currentLocation) {
            prevLocation=currentLocation;
            findViewById(R.id.RelativeLayout).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    if (arg1.getAction()==MotionEvent.ACTION_UP)
                    {
                        applyTheme.nextTheme();
                        applyTheme.all(applyTheme.prevLocation);
                        if (applyTheme.prevLocation==8)
                            gameHandler.updateContent();
                        if (applyTheme.prevLocation==6)
                            shipsInput.show();
                    }
                    return true;
                }
            });

            switch (currentLocation) {
                case 0:
                    mainMenu();
                    break;
                case 1:
                    gameMode();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    settingsScreen();
                    break;
                case 5:
                    playernameScreen();
                    break;
                case 6:
                    shipsInput();
                    break;
                case 7:
                    waitingScreen();
                    break;
                case 8:
                    nextPlayer();
                    break;
                case 9:
                    endOfGame();
                    break;
                case 10:
                    help();
                default:
                    break;
            }
        };

        private void mainMenu() {
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            findViewById(R.id.BackgroundIm).setBackgroundColor(getColor(colors[theme][0]));
            ((TextView) findViewById(R.id.textView)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.l1_button_start)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.l1_button_start)).setBackgroundColor(getColor(R.color.Transparent));
            ((Button) findViewById(R.id.l1_button_tutorial)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.l1_button_tutorial)).setBackgroundColor(getColor(R.color.Transparent));
            ((Button) findViewById(R.id.l1_button_settings)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.l1_button_settings)).setBackgroundColor(getColor(R.color.Transparent));
            ((Button) findViewById(R.id.l1_button_credits)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.l1_button_credits)).setBackgroundColor(getColor(R.color.Transparent));
            ((Button) findViewById(R.id.l1_button_exit)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.l1_button_exit)).setBackgroundColor(getColor(R.color.Transparent));
            ((ImageView) findViewById(R.id.ShipLogo)).setImageResource(getDrawableID("shiplogo" + theme));

            //set font
            setFont(R.id.textView, "DOTSHORM.ttf");
            setFont(R.id.l1_button_start, "DOTSHORM.ttf");
            setFont(R.id.l1_button_credits, "DOTSHORM.ttf");
            setFont(R.id.l1_button_exit, "DOTSHORM.ttf");
            setFont(R.id.l1_button_settings, "DOTSHORM.ttf");
            setFont(R.id.l1_button_tutorial, "DOTSHORM.ttf");

        }

        private void playernameScreen() {
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((EditText) findViewById(R.id.input_playernames_input1)).setTextColor(getColor(colors[theme][3]));
            ((EditText) findViewById(R.id.input_playernames_input2)).setTextColor(getColor(colors[theme][3]));
            ((ImageButton) findViewById(R.id.input_playernames_button_accept)).setColorFilter(getColor(colors[theme][4]), PorterDuff.Mode.MULTIPLY);
            ((ImageButton) findViewById(R.id.input_playernames_button_return)).setColorFilter(getColor(colors[theme][4]), PorterDuff.Mode.MULTIPLY);
            findViewById(R.id.input_playernames_button_accept).setBackgroundColor(getColor(R.color.Transparent));
            findViewById(R.id.input_playernames_button_return).setBackgroundColor(getColor(R.color.Transparent));
            ((TextView) findViewById(R.id.Player1title)).setTextColor(getColor(colors[theme][2]));
            ((TextView) findViewById(R.id.Player2title)).setTextColor(getColor(colors[theme][2]));

            //set height width
            setViewHeight(R.id.input_playernames_button_accept, buttonSize);
            setViewWidth(R.id.input_playernames_button_accept, buttonSize);
            setViewHeight(R.id.input_playernames_button_return, buttonSize);
            setViewWidth(R.id.input_playernames_button_return, buttonSize);

            //set font
            setFont(R.id.input_playernames_input1, "DOTSHORM.ttf");
            setFont(R.id.input_playernames_input2, "DOTSHORM.ttf");
            setFont(R.id.Player1title, "DOTSHORM.ttf");
            setFont(R.id.Player2title, "DOTSHORM.ttf");
        };

        private void waitingScreen() {
            ((TextView) findViewById(R.id.textView)).setTextColor(getColor(colors[theme][2]));
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((ImageButton)findViewById(R.id.button)).setColorFilter(getColor(colors[theme][2]));
            Animation pulse = AnimationUtils.loadAnimation(MainActivity.this, R.anim.pulse);
            findViewById(R.id.button).startAnimation(pulse);

            //set font
            setFont(R.id.textView, "DOTSHORM.ttf");
        }

        private void shipsInput() {
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((TextView) findViewById(R.id.textView6)).setTextColor(getColor(colors[theme][3]));
            setMargins(R.id.textView6, buttonSize+5, 0, buttonSize+5, 0);

            ImageButton OkButton=(ImageButton)findViewById(R.id.OkButton);
            OkButton.setColorFilter(getColor(colors[theme][4]), PorterDuff.Mode.MULTIPLY);
            findViewById(R.id.OkButton).setBackgroundColor(getColor(R.color.Transparent));
            setMargins(R.id.OkButton, 0, 0, 0, 19);
            setViewHeight(R.id.OkButton, buttonSize);
            setViewWidth(R.id.OkButton, buttonSize);

            ImageButton BackButton=(ImageButton)findViewById(R.id.PlayAgainButton);
            setMargins(R.id.PlayAgainButton, 0, 0, 0, 19);
            setViewHeight(R.id.PlayAgainButton, buttonSize);
            setViewWidth(R.id.PlayAgainButton, buttonSize);
            BackButton.setBackgroundColor(getColor(R.color.Transparent));
            BackButton.setColorFilter(getColor(colors[theme][4]), PorterDuff.Mode.MULTIPLY);

            //set font
            setFont(R.id.textView6, "DOTSHORM.ttf");
        }

        private void nextPlayer() {
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((Button) findViewById(R.id.NextButton)).setTextColor(getColor(colors[theme][0]));
            findViewById(R.id.NextButton).setBackgroundColor(getColor(colors[theme][2]));
            findViewById(R.id.HelpButton).setBackgroundColor(getColor(R.color.Transparent));
            ((ImageButton)findViewById(R.id.HelpButton)).setColorFilter(getColor(colors[theme][2]));
            Animation pulse = AnimationUtils.loadAnimation(MainActivity.this, R.anim.pulse);
            findViewById(R.id.HelpButton).startAnimation(pulse);

            //font
            setFont(R.id.NextButton, "DOTSHORM.ttf");
        };

        private void settingsScreen() {
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((TextView)findViewById(R.id.textView4)).setTextColor(getColor(colors[theme][2]));
            ((TextView)findViewById(R.id.textView8)).setTextColor(getColor(colors[theme][2]));

            //font
            setFont(R.id.textView4, "DOTSHORM.ttf");
            setFont(R.id.textView8, "DOTSHORM.ttf");

        } // TODO: WRITE

        private void gameMode() {
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((Button)findViewById(R.id.SingleplayerButton)).setTextColor(getColor(colors[theme][2]));
            findViewById(R.id.SingleplayerButton).setBackgroundColor(getColor(R.color.Transparent));
            ((Button)findViewById(R.id.MultiplayerButton)).setTextColor(getColor(colors[theme][2]));
            findViewById(R.id.MultiplayerButton).setBackgroundColor(getColor(R.color.Transparent));

            //font
            setFont(R.id.SingleplayerButton, "DOTSHORM.ttf");
            setFont(R.id.MultiplayerButton, "DOTSHORM.ttf");
        }

        private void endOfGame() {
            findViewById(R.id.MainMenuButton).setBackgroundColor(getColor(colors[theme][4]));
            findViewById(R.id.PlayAgainButton).setBackgroundColor(getColor(colors[theme][4]));
            ((Button) findViewById(R.id.MainMenuButton)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.PlayAgainButton)).setTextColor(getColor(colors[theme][2]));
            ((Button) findViewById(R.id.MainMenuButton)).setBackgroundColor(getColor(R.color.Transparent));
            ((Button) findViewById(R.id.PlayAgainButton)).setBackgroundColor(getColor(R.color.Transparent));
            findViewById(R.id.RelativeLayout).setBackgroundColor(getColor(colors[theme][0]));
            ((TextView) findViewById(R.id.textView6)).setTextColor(getColor(colors[theme][2]));

            //font
            setFont(R.id.MainMenuButton, "DOTSHORM.ttf");
            setFont(R.id.PlayAgainButton, "DOTSHORM.ttf");
            setFont(R.id.textView6, "DOTSHORM.ttf");

        }

        private void help() {
            ((TextView)findViewById(R.id.help1)).setTextColor(colors[theme][2]);
            ((TextView)findViewById(R.id.help2)).setTextColor(colors[theme][2]);
            ((ImageView)findViewById(R.id.help_arrow1)).setColorFilter(colors[theme][2], PorterDuff.Mode.MULTIPLY);
            ((ImageView)findViewById(R.id.help_arrow2)).setColorFilter(colors[theme][2], PorterDuff.Mode.MULTIPLY);
        }

        public void setMargins(int id, int left, int top, int right, int bottom) {
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)findViewById(id).getLayoutParams();
            p.setMargins(left, top, right, bottom);
            findViewById(id).setLayoutParams(p);
        }

        public void setFont(int textViewId, String font) {
            ((TextView)findViewById(textViewId)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/"+font));
        }

        public void setInvisible(int id) {
            findViewById(id).setVisibility(View.INVISIBLE);
        }

        public void setVisible(int id) {
            findViewById(id).setVisibility(View.VISIBLE);
        }
    }

    private final class Player {
        private String name;

        Table table;
        Table tableEnemy;
        int shipsLeftInGame;


        public int ships_already_input;

        public int shipsCounts[];

        public Player(String name) {
            this.name = name;
            init();
        }

        private void init() {
            shipsLeftInGame=10;
            ships_already_input = 0;
            shipsCounts = new int[5];
            Arrays.fill(shipsCounts, 0);
        };

        public boolean addShip(index p1, index p2) {
            int x1 = p1.i;
            int x2 = p2.i;
            int y1 = p1.j;
            int y2 = p2.j;

            if (x1 == x2) {
                int size = Math.abs(y2 - y1)+1;
                if (shipsCounts[size]>=5-size)
                {
                    //cancel add
                    return false;
                }
                ++shipsCounts[size];
                ++ships_already_input;
                return true;
            }
            else if (y1 == y2) {
                int size = Math.abs(x2 - x1)+1;
                if (shipsCounts[size]>=5-size)
                {
                    //cancel add
                    return false;
                }
                ++shipsCounts[size];
                ++ships_already_input;
                return true;
            }
            else return false;
        }

        boolean checkMoveStart(float sx, float sy)
        {
            index start_pos = table.getIndices(sx, sy);
            if (!table.checkFreeBlock(start_pos, start_pos))
                return false;
            table.setTakenBlock(start_pos, start_pos);
            return true;
        }

        boolean placeShip(float start_x, float start_y, float end_x, float end_y) {
            index pos_start = table.getIndices(start_x, start_y);
            index pos_end = table.getIndices(end_x, end_y);
            pos_end.normalize(pos_start, 3);
            pos_end.adjustSameLine(pos_start);
            boolean added = addShip(pos_start, pos_end);
            if (added)
                table.setShipBlock(pos_start, pos_end);
            else
                table.removeTakenBlock(pos_start, pos_end);
            return added;
        }

        boolean checkMove(float sx, float sy, float ox, float oy, float nx, float ny) {
            index start_pos = table.getIndices(sx, sy);
            index old_pos = table.getIndices(ox, oy);

            //adjust size <= 4
            old_pos.normalize(start_pos, 3);
            index new_pos = table.getIndices(nx, ny);
            new_pos.normalize(start_pos, 3);
            if (nx ==0&&ny==0)
                new_pos=old_pos;

            old_pos.adjustSameLine(start_pos);
            new_pos.adjustSameLine(start_pos);

            if (old_pos!=new_pos && !table.checkFreeBlock(start_pos, new_pos)) {
                table.removeTakenBlock(start_pos, old_pos);
                return false;
            }
            table.removeTakenBlock(start_pos, old_pos);
            table.setTakenBlock(start_pos, new_pos);
            return true;
        }

        boolean deleteShip(float x, float y)
        {
            index pos = table.getIndices(x, y);
            if (!table.isShip(pos))
                return false;

            int i1 = pos.i, i2 = pos.i, j1 = pos.j, j2 = pos.j;

            while (i1>0 && table.isShip(new index(i1-1, pos.j)))
                --i1;

            while (j1>0 && table.isShip(new index(pos.i, j1-1)))
                --j1;

            while (i2<table.length_x-1 && table.isShip(new index(i2+1, pos.j)))
                ++i2;

            while (j2<table.length_y-1 && table.isShip(new index(pos.i, j2+1)))
                ++j2;
            boolean removed;
                removed = removeShip(new index(i1, j1), new index(i2, j2));
            if (removed)
            {
                table.removeShipBlock(new index(i1, j1), new index(i2, j2));
                return true;
            } else return false;
        }

        public boolean removeShip(index p1, index p2) {
            if (ships_already_input==0)
                return false;
            int i1 = min(p1.i, p2.i);
            int j1 = min(p1.j, p2.j);
            int sz;
            if (p1.i==p2.i)
                sz=Math.abs(p1.j-p2.j)+1;
            else
                sz=Math.abs(p1.i-p2.i)+1;
            --shipsCounts[sz];
            --ships_already_input;
            return true;
        }

        public void increaseScore() {
            //SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sp.edit();
            //editor.putInt(getString(R.string.saved_theme_key), theme);
            //editor.apply();
        }
    }

    public class pair {
        public index p1, p2;

        pair(index p1, index p2) {
            this.p1=p1;
            this.p2=p2;
        }
    }

    private class Table {
        private cell tbl[][];
        public int length_x, length_y;
        public String cellWidgetName;
        public View.OnTouchListener listener;
        private int step;

        Table(int x, int y, String cellWidgetName, View.OnTouchListener listener) {
            length_x = x;
            length_y = y;
            tbl = new cell[x][y];
            this.cellWidgetName = cellWidgetName;
            this.listener = listener;
            init();
        }

        private void init() {
            for (int i = 0; i < tbl.length; ++i)
                for (int j = 0; j < tbl[0].length; ++j)
                {
                    tbl[i][j] = new cell(getViewID(cellWidgetName + Integer.toString(i) + Integer.toString(j)), false, 0);
                    tbl[i][j].setSimpleDrawable();
                }
        }

        private void reInit(String cellWidgetName) {
            this.cellWidgetName=cellWidgetName;
            for (int i = 0; i < tbl.length; ++i)
                for (int j = 0; j < tbl[0].length; ++j)
                {
                    if (findViewById(tbl[i][j].viewID)!=null)
                    {
                        findViewById(tbl[i][j].viewID).setOnTouchListener(EmptyListener);
                    }
                    tbl[i][j].viewID=getViewID(cellWidgetName + Integer.toString(i) + Integer.toString(j));
                    findViewById(tbl[i][j].viewID).setOnTouchListener(EmptyListener);
                    listener=EmptyListener;
                    tbl[i][j].show();
                }
        }

        public void show() {
            for (int i = 0; i < length_x; ++i)
                for (int j = 0; j<length_y; ++j)
                {
                    findViewById(tbl[i][j].viewID).setOnTouchListener(listener);
                    tbl[i][j].show();
                }

            step=findViewById(tbl[0][0].viewID).getWidth();
        }

        public index getIndices(float x, float y) {
            step = myGetW(findViewById(tbl[2][0].viewID));          // TODO: check if needed
            int j = 0;
            if (myGetL(findViewById(tbl[0][j].viewID)) > x)
                j = -1;
            else {
                while (j < tbl.length && myGetL(findViewById(tbl[0][j].viewID)) + step < x + step / 4)
                    ++j;
            }

            int top_corner = myGetT(findViewById(tbl[1][0].viewID)) - step;
            int cur = 0;
            int i = 0;
            if (top_corner > y)
                i = -1;
            else {
                cur = top_corner + step;
                int it = cur;
                while (i < tbl.length && it < y + step / 4) {
                    it += step;
                    ++i;
                }
            }
            index res = new index(i, j);
            res.normalize(0, tbl.length-1);
            return res;
        }

        public boolean isTaken(index p) {
            return tbl[p.i][p.j].state == 1;
        }
        public boolean isMiss(index p) {return isFree(p);};

        public boolean isFree(index p) {
            return tbl[p.i][p.j].state == 0;
        }
        public boolean isUnknown(index p) {return isFree(p);};

        public boolean isNearShip(index p) {
            return tbl[p.i][p.j].state == 2 || tbl[p.i][p.j].state==4;
        }
        public boolean isInjured(index p) {return isNearShip(p);};

        public boolean isShip(index p) {
            return tbl[p.i][p.j].state == 3;
        }
        public boolean isDead(index p) {return isShip(p);};

        public void setTakenBlock(index p1, index p2) {
            p1.normalize(0, 9);
            p2.normalize(0, 9);
            int H = max(p1.i, p2.i);
            int W = max(p1.j, p2.j);
            int x = min(p1.i, p2.i);
            int y = min(p1.j, p2.j);
            for (int i = x; i <= H; ++i)
                for (int j = y; j <= W; ++j)
                {
                    if (tbl[i][j].state == 0)
                        tbl[i][j].setState(1);
                    tbl[i][j].updateColor(7);
                }
        }

        public void removeTakenBlock(index p1, index p2) {
            p1.normalize(0, 9);
            p2.normalize(0, 9);
            int H = max(p1.i, p2.i);
            int W = max(p1.j, p2.j);
            int x = min(p1.i, p2.i);
            int y = min(p1.j, p2.j);
            for (int i = x; i <= H; ++i)
                for (int j = y; j <= W; ++j)
                {
                    if (tbl[i][j].state == 1)
                        tbl[i][j].setState(0);
                    tbl[i][j].updateColor(6);
                }
        }

        public void setNearShipBlock(index p1, index p2){
            p1.normalize(0, 9);
            p2.normalize(0, 9);
            int H = max(p1.i, p2.i);
            int W = max(p1.j, p2.j);
            int x = min(p1.i, p2.i);
            int y = min(p1.j, p2.j);
            for (int i = x; i <= H; ++i)
                for (int j = y; j <= W; ++j)
                {
                    if (tbl[i][j].state==2)
                        tbl[i][j].setState(4);
                    else tbl[i][j].setState(2);
//                    tbl[i][j].updateColor(0);
                }
        }

        public void setShipBlock(index p1, index p2) {
            int H = max(p1.i, p2.i);
            int W = max(p1.j, p2.j);
            int x = min(p1.i, p2.i);
            int y = min(p1.j, p2.j);
            int curShipColor = ThreadLocalRandom.current().nextInt(8, 10 + 1);
            setNearShipBlock(new index(x-1, y-1), new index(H+1, W+1));
            for (int i = x; i <= H; ++i)
                for (int j = y; j <= W; ++j)
                {
                    tbl[i][j].setState(3);
                    tbl[i][j].updateColor(curShipColor);
                }
            int n = 4;
        }

        public void removeShipBlock(index p1, index p2) {
            index l = new index(min(p1.i, p2.i)-1, min(p1.j, p2.j)-1);
            index r = new index(max(p1.i, p2.i)+1, max(p1.j, p2.j)+1);
            l.normalize(0, 9);
            r.normalize(0, 9);
            for (int i = l.i; i <= r.i; ++i)
                for (int j = l.j; j <= r.j; ++j)
                {
                    if (tbl[i][j].state==4)
                        tbl[i][j].setState(2);
                    else if (tbl[i][j].state == 2 || tbl[i][j].state==3)
                        tbl[i][j].setState(0);
                    tbl[i][j].updateColor(6);
                }
        }

        public boolean checkFreeBlock(index p1, index p2) {
            p1.normalize(0, 9);
            p2.normalize(0, 9);
            int H = max(p1.i, p2.i);
            int W = max(p1.j, p2.j);
            int x = min(p1.i, p2.i);
            int y = min(p1.j, p2.j);
            boolean res = true;
            for (int i = x; i <= H; ++i){
                for (int j = y; j <= W; ++j) {
                    if (tbl[i][j].state != 0 && tbl[i][j].state != 1) {
                        res = false;
                        break;
                    }
                }
            }
            return res;
        }

        public void setMiss(index p1, index p2) {
            p1.normalize(0, 9);
            p2.normalize(0, 9);
            int l1=min(p1.i, p2.i);
            int r1=max(p1.i, p2.i);
            int l2=min(p1.j, p2.j);
            int r2=max(p1.j, p2.j);
            for (int i =l1; i<= r1; ++i)
                for (int j = l2; j<=r2;++j) {
                    if (tbl[i][j].state==0) {
                        tbl[i][j].setState(1);
                        tbl[i][j].setMiss();
                    }
                }
        }

        public void setInjured(index p){
            tbl[p.i][p.j].setState(2);
            tbl[p.i][p.j].updateColor(7);
        };

        private void setDeadBlock(index p1, index p2, int color) {
            int H = max(p1.i, p2.i);
            int W = max(p1.j, p2.j);
            int x = min(p1.i, p2.i);
            int y = min(p1.j, p2.j);
            for (int i = x; i <= H; ++i)
                for (int j = y; j <= W; ++j)
                {
                    tbl[i][j].setState(3);
                    tbl[i][j].updateColor(color);
                }
        }

        public pair setDead(index p, int color) {
            if ((p.i-1>=0&&isInjured(new index(p.i-1, p.j))) || (p.i+1<length_x&&isInjured(new index(p.i+1, p.j)))) {
                int x1=p.i-1;
                while (x1>=0&&isInjured(new index(x1, p.j)))
                    --x1;
                int x2=p.i+1;
                while (x2<length_x && isInjured(new index(x2, p.j)))
                    ++x2;
                setDeadBlock(new index(x1+1, p.j), new index(x2-1, p.j), color);
                setMiss(new index(x1, p.j-1), new index(x2, p.j+1));
                return new pair(new index(x1+1, p.j), new index(x2-1, p.j));

            } else if ((p.j-1>=0&&isInjured(new index(p.i, p.j-1))) || (p.j+1<length_y&&isInjured(new index(p.i, p.j+1)))) {
                int x1 = p.j - 1;
                while (x1 >= 0 && isInjured(new index(p.i, x1)))
                    --x1;
                int x2 = p.j + 1;
                while (x2 < length_y && isInjured(new index(p.i, x2)))
                    ++x2;
                setDeadBlock(new index(p.i, x1+1), new index(p.i, x2-1), color);
                setMiss(new index(p.i-1, x1), new index(p.i+1, x2));
                return new pair(new index(p.i, x1+1), new index(p.i, x2-1));
            } else {
                setDeadBlock(p, p, color);
                setMiss(new index(p.i-1, p.j-1), new index(p.i+1, p.j+1));
                return new pair(p, p);
            }
        }

        private void setCrossed(index p1, index p2) {
            p1.normalize(0, 9);
            p2.normalize(0, 9);

            int l1=min(p1.i, p2.i);
            int r1=max(p1.i, p2.i);
            int l2=min(p1.j, p2.j);
            int r2=max(p1.j, p2.j);

            for (int i = l1; i <= r1; ++i)
                for (int j = l2; j <= r2; ++j)
                    tbl[i][j].alpha=0.0f;
        }

        private void setHalfCrossed(index p) {
            p.normalize(0, 9);
            tbl[p.i][p.j].alpha=0.5f;
        }

    }

    class cell {
        private int additionalColor = 0;
        public int viewID;
        LayerDrawable drawable;
        public boolean taken;
        public int state;
        float alpha;
        public int color;
        // 0 - empty, 1 - taken, 2 - near ship, 3 - ship, 4 - taken twice
        //0 - unknown, 1 - miss, 2 - wounded, 3 - dead

        cell(int viewID, boolean taken, int state) {
            alpha=1.0f;
            this.viewID = viewID;
            this.taken = taken;
            setState(state);
        }

        public void setState(int state) {
            if (state < 0 || state > 4)
                return;
            this.state = state;
        }

        public void updateColor(int color) {
            this.color=color;
            Drawable d1 = this.drawable.getDrawable(0);
            d1 = applyTheme.updateColor(d1, color);
            this.drawable.setDrawable(0, d1);
        }

        public void updateSecondColor() {
            if (drawable.getNumberOfLayers()!=2)
                return;
            Drawable d2 = drawable.getDrawable(1);
            d2 = applyTheme.updateColor(d2, additionalColor);
            this.drawable.setDrawable(1, d2);
        }

        public void show() {
            updateColor(this.color);
            updateSecondColor();
            ((ImageView)findViewById(viewID)).setImageDrawable(drawable);
            ((ImageView)findViewById(viewID)).setAlpha(alpha);
        }

        public void setSimpleDrawable() {
            this.drawable=new LayerDrawable(new Drawable[]{getDrawable(R.drawable.cell)});
            this.drawable.mutate();
            updateColor(6);
        }

        public void setMiss() {
            this.drawable = new LayerDrawable(new Drawable[]{getDrawable(R.drawable.cell)});
            this.drawable.mutate();
            Drawable d = getDrawable(R.drawable.cell_miss);
            d = applyTheme.updateColor(d, 7);
            this.drawable.addLayer(d);
            ((ImageView)findViewById(viewID)).setImageDrawable(this.drawable);
        }


        public void updateAlpha(float alpha) {
            this.alpha=alpha;
        }
    }

    private int myGetL(View v) {
        Rect rect = new Rect();
        v.getGlobalVisibleRect(rect);
        return rect.left;
    }

    private int myGetT(View v) {
        Rect rect = new Rect();
        v.getGlobalVisibleRect(rect);
        return rect.top;
    }

    private int myGetW(View v) {
        Rect rect = new Rect();
        v.getGlobalVisibleRect(rect);
        return rect.right - rect.left;
    }


    private class ShipsInput {

        private boolean first_input;
        private String cellWidgetName = "ib";

        ShipsInput() {
            currentLocation=6;
            first_input=true;
            findViewById(R.id.OkButton).setOnTouchListener(ToGame_OkButton_listener);
            findViewById(R.id.PlayAgainButton).setOnTouchListener(BackButton_listener);
            applyTheme.all(currentLocation);
        }

        public void initTable() {
            if (first_input) {
                player_1.table = new Table(FIELD_SIZE, FIELD_SIZE, "ib", InputShipsButton_listener);
                player_1.table.show();
            } else {
                player_2.table = new Table(FIELD_SIZE, FIELD_SIZE, "ib", InputShipsButton_listener);
                player_2.table.show();
            }
            int n = 4;
        }

        public void show() {
            if (first_input)
                player_1.table.show();
            else player_2.table.show();
        }

        boolean checkEndOfInput() {
            if (first_input) {
                if (player_1.ships_already_input == 10) {
                    findViewById(R.id.OkButton).setVisibility(View.VISIBLE);
                }
                return false;
            } else if (player_2.ships_already_input == 10) {
                findViewById(R.id.OkButton).setVisibility(View.VISIBLE);
                return true;
            }
            return false;
        }

        private View.OnTouchListener InputShipsButton_listener = new View.OnTouchListener() {
            boolean movable;
            float start_x;
            float start_y;
            float prev_x;
            float prev_y;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gD.onTouchEvent(motionEvent)) {
                    start_x = motionEvent.getRawX();
                    start_y = motionEvent.getRawY();
                    if (first_input) {
                        if ( player_1.deleteShip(start_x, start_y)) {
                            if (player_1.ships_already_input==9)
                                applyTheme.setInvisible(R.id.OkButton);
                            show();
                            return true;
                        }
                    }
                    else if (player_2.deleteShip(start_x, start_y)) {
                        if (player_2.ships_already_input==9)
                            applyTheme.setInvisible(R.id.OkButton);
                        show();
                        return true;
                    }
                }
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        start_x = motionEvent.getRawX();
                        start_y = motionEvent.getRawY();
                        prev_x = start_x;
                        prev_y = start_y;
                        if (first_input)
                            movable = player_1.checkMoveStart(start_x, start_y);
                        else movable = player_2.checkMoveStart(start_x, start_y);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (!movable)
                            break;
                        float new_x = motionEvent.getRawX();
                        float new_y = motionEvent.getRawY();
                        if (first_input)
                            movable = player_1.checkMove(start_x, start_y, prev_x, prev_y, new_x, new_y);
                        else
                            movable = player_2.checkMove(start_x, start_y, prev_x, prev_y, new_x, new_y);
                        prev_x = new_x;
                        prev_y = new_y;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!movable)
                            break;

                        boolean added;
                        if (first_input) {
                            movable = player_1.placeShip(start_x, start_y, prev_x, prev_y);
                            player_1.table.show();
                        } else {
                            movable = player_2.placeShip(start_x, start_y, prev_x, prev_y);
                            player_2.table.show();
                        }
                        checkEndOfInput();
                        break;
                    default:
                        return false;
                }
                show();
                return true;
            }
        };

        private View.OnTouchListener ToGame_OkButton_listener = new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (player_2.ships_already_input==10) {
                    toGame();
                }
                else {
                    first_input = false;
                    initTable();
                    ((TextView)findViewById(R.id.textView6)).setText(player_2.name + ", place your ships");
                    applyTheme.setInvisible(R.id.OkButton);
                }
                return false;
            };
        };
    };

    private class GameHandler {
        boolean firstPlayerTurn;
        boolean ableToDoStep;
        String cellStrName1 = "pt";
        String cellStrName2 = "ib";
        boolean needToInit;

        GameHandler() {
            firstPlayerTurn = true;
            needToInit=true;
            toWaitingScreen();
        }

        public void initTables() {
            player_1.tableEnemy = new Table(FIELD_SIZE, FIELD_SIZE, cellStrName2, GameEnemyButton_listener);
            player_2.tableEnemy = new Table(FIELD_SIZE, FIELD_SIZE, cellStrName2, GameEnemyButton_listener);
            player_1.table.reInit(cellStrName1);
            player_2.table.reInit(cellStrName1);
        }

        void toNextPlayer() {
            currentLocation=8;
            setContentView(R.layout.activity_game);
            applyTheme.all(currentLocation);
            if (needToInit)
            {
                initTables();
                needToInit=false;
            }
            ableToDoStep=true;
            findViewById(R.id.NextButton).setOnTouchListener(NextButtonListener);
            findViewById(R.id.HelpButton).setOnTouchListener(HelpButtonListener);
            gameSetLayoutParams();
            updateContent();
        }

        void toWaitingScreen() {
            currentLocation=7;
            setContentView(R.layout.activity_waiting_screen);
            applyTheme.all(currentLocation);
            if (firstPlayerTurn)
                ((TextView)findViewById(R.id.textView)).setText(player_1.name + ", it's your turn.");
            else
                ((TextView)findViewById(R.id.textView)).setText(player_2.name + ", it's your turn.");
            findViewById(R.id.button).setOnTouchListener(NextPlayerListener);
        }

        void gameSetLayoutParams() {
            int w = (getDisplayWidth())/2-10;
            (findViewById(R.id.GridLayout)).setY((getDisplayHeight()-w)/2);
            setViewWidth(R.id.PlayerTable, w);
            setViewWidth(R.id.EnemyTable, w);
            int h = w/10;
            setViewHeight(R.id.ll1, h);
            setViewHeight(R.id.ll2, h);
            setViewHeight(R.id.ll3, h);
            setViewHeight(R.id.ll4, h);
            setViewHeight(R.id.ll5, h);
            setViewHeight(R.id.ll6, h);
            setViewHeight(R.id.ll7, h);
            setViewHeight(R.id.ll8, h);
            setViewHeight(R.id.ll9, h);
            setViewHeight(R.id.ll0, h);
            setViewHeight(R.id.ll10, h);
            setViewHeight(R.id.ll20, h);
            setViewHeight(R.id.ll30, h);
            setViewHeight(R.id.ll40, h);
            setViewHeight(R.id.ll50, h);
            setViewHeight(R.id.ll60, h);
            setViewHeight(R.id.ll70, h);
            setViewHeight(R.id.ll80, h);
            setViewHeight(R.id.ll90, h);
            setViewHeight(R.id.ll100, h);
        }

        void updateContent()
        {
            if (firstPlayerTurn)
            {
                player_1.table.show();
                player_1.tableEnemy.show();
            } else {
                player_2.table.show();
                player_2.tableEnemy.show();
            }
        }

        private boolean checkShipsAround(index p)
        {
            if (firstPlayerTurn)
            {
                if (p.i-1>=0&&player_2.table.isShip(new index(p.i-1, p.j)) && player_1.tableEnemy.isUnknown(new index(p.i-1, p.j)))
                    return true;
                if (p.i+1<10&&player_2.table.isShip(new index(p.i+1, p.j)) && player_1.tableEnemy.isUnknown(new index(p.i+1, p.j)))
                    return true;
                if (p.j-1>=0&&player_2.table.isShip(new index(p.i, p.j-1)) && player_1.tableEnemy.isUnknown(new index(p.i, p.j-1)))
                    return true;
                if (p.j+1<10&&player_2.table.isShip(new index(p.i, p.j+1)) && player_1.tableEnemy.isUnknown(new index(p.i, p.j+1)))
                    return true;
            } else {
                if (p.i-1>=0&&player_1.table.isShip(new index(p.i-1, p.j)) && player_2.tableEnemy.isUnknown(new index(p.i-1, p.j)))
                    return true;
                if (p.i+1<10&&player_1.table.isShip(new index(p.i+1, p.j)) && player_2.tableEnemy.isUnknown(new index(p.i+1, p.j)))
                    return true;
                if (p.j-1>=0&&player_1.table.isShip(new index(p.i, p.j-1)) && player_2.tableEnemy.isUnknown(new index(p.i, p.j-1)))
                    return true;
                if (p.j+1<10&&player_1.table.isShip(new index(p.i, p.j+1)) && player_2.tableEnemy.isUnknown(new index(p.i, p.j+1)))
                    return true;
            }
            return false;
        }

        int checkPosition(float x, float y) {
            index pos;
            if (firstPlayerTurn) {
                pos = player_1.tableEnemy.getIndices(x, y);
                if (!player_1.tableEnemy.isUnknown(pos))
                    return -1;
                if (player_2.table.isShip(pos)) {
                    if (!checkShipsAround(pos)) {
                        --player_2.shipsLeftInGame;
                        int color = player_2.table.tbl[pos.i][pos.j].color;
                        pair ship_pos = player_1.tableEnemy.setDead(pos, color);
                        player_2.table.setCrossed(ship_pos.p1, ship_pos.p2);
                        return 2;
                    } else {
                        player_1.tableEnemy.setInjured(pos);
                        player_2.table.setHalfCrossed(pos);
                        return 1;
                    }
                } else {
                    player_1.tableEnemy.setMiss(pos, pos);
                    return 0;
                }
            } else {
                pos = player_2.tableEnemy.getIndices(x, y);
                if (!player_2.tableEnemy.isUnknown(pos))
                    return -1;
                if (player_1.table.isShip(pos)) {
                    if (!checkShipsAround(pos)) {
                        --player_1.shipsLeftInGame;
                        int color = player_1.table.tbl[pos.i][pos.j].color;
                        pair ship_pos=player_2.tableEnemy.setDead(pos, color);
                        player_1.table.setCrossed(ship_pos.p1, ship_pos.p2);
                        return 2;
                    } else {
                        player_2.tableEnemy.setInjured(pos);
                        player_1.table.setHalfCrossed(pos);
                        return 1;
                    }
                } else {
                    player_2.tableEnemy.setMiss(pos, pos);
                    return 0;
                }
            }
        }

        private final View.OnTouchListener NextPlayerListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    firstPlayerTurn=!firstPlayerTurn;
                    toNextPlayer();
                }
                return false;
            }
        };

        private final View.OnTouchListener NextButtonListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    toWaitingScreen();
                return false;
            }
        };

        private final View.OnTouchListener HelpButtonListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    findViewById(R.id.RelativeLayoutHelp).setVisibility(View.VISIBLE);
                    applyTheme.all(10);
                    findViewById(R.id.HelpUndoButton).setOnTouchListener(HelpButtonListenerUndo);
                }
                return false;
            }
        };

        private final View.OnTouchListener HelpButtonListenerUndo = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    findViewById(R.id.RelativeLayoutHelp).setVisibility(View.INVISIBLE);
                }
                return false;
            }
        };


        private final View.OnTouchListener GameEnemyButton_listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if (!ableToDoStep)
                        return false;
                    float x = motionEvent.getRawX();
                    float y = motionEvent.getRawY();
                    int info = checkPosition(x, y);
                    updateContent();
                    if (info==0)
                    {
                        findViewById(R.id.NextButton).setVisibility(View.VISIBLE);
                        ableToDoStep=false;
                    }
                    checkEndOfGame();
                }
                return false;
            }
        };


        void checkEndOfGame() {
            if (player_1.shipsLeftInGame==0 || player_2.shipsLeftInGame==0) {
                currentLocation = 9;
                setContentView(R.layout.activity_end_of_game);
                applyTheme.all(currentLocation);
                if (player_1.shipsLeftInGame == 0) {
                    ((TextView)findViewById(R.id.textView6)).setText(player_1.name+ ", you won");
//                    player_1.increseRating();
                } else {
                    ((TextView)findViewById(R.id.textView6)).setText(player_2.name+ ", you won");
//                    player_2.increaseRating();
                }
                findViewById(R.id.PlayAgainButton).setOnTouchListener(PlayAgainListener);
                findViewById(R.id.MainMenuButton).setOnTouchListener(BackButton_listener);
            }
        }

        private final View.OnTouchListener GamePlayerButton_listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            };
        };

        private final View.OnTouchListener PlayAgainListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                toShipsInput(player_1.name, player_2.name);
                return false;
            };
        };
    }


    private View.OnTouchListener StartButton_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                toGameModeScreen();
            return false;
        }
    };

    private View.OnTouchListener ExitButton_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                finish();
                System.exit(0);
            }
            return false;
        }
    };

    private View.OnTouchListener SettingsButton_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                toSettings();
            return false;
        }
    };

    private View.OnTouchListener OkButton_listener = new View.OnTouchListener() {

        String playername_1, playername_2;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    playername_1 = ((EditText) findViewById(R.id.input_playernames_input1)).getText().toString();
                    playername_2 = ((EditText) findViewById(R.id.input_playernames_input2)).getText().toString();
                    break;
                case MotionEvent.ACTION_UP:
                    toShipsInput(playername_1, playername_2);
                    break;
                default:
                    break;
            }
            return false;
        }
    };



    private final View.OnTouchListener BackButton_listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                toMainMenu();
            return false;
        }
    };

    private final View.OnTouchListener SingleplayerButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                toPlayernameScreen(true);
            }
            return false;
        }
    };

    private final View.OnTouchListener MultiplayerButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                toPlayernameScreen(false);
            }
            return false;
        }
    };



    private final View.OnTouchListener EmptyListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };


    private class SingleTapConfirm extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
    }

    private final View.OnTouchListener ThemeButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                //update settings
                applyTheme.nextTheme();
                //save settings
                applyTheme.saveTheme();
                //apply settings on current screen
                applyTheme.all(currentLocation);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gD = new GestureDetector(this, new SingleTapConfirm());


        applyTheme=new ApplyTheme();
        toMainMenu();
    }

    private void setThemeSwitchingListener() {
        findViewById(R.id.RelativeLayout).setOnTouchListener(ThemeButtonListener);
    }

    private void setViewHeight(int id, int newHeight) {
        // Gets layout
        View layout = findViewById(id);
        // Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = newHeight;
        layout.setLayoutParams(params);
    }

    private void setViewWidth(int id, int newWidth) {
        // Gets layout
        View layout = (View)findViewById(id);
        // Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.width = newWidth;
        layout.setLayoutParams(params);
    }

    private int getDisplayHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private int getDisplayWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    protected void toMainMenu() {
        currentLocation=0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        applyTheme.all(currentLocation);
        Drawable d1=getDrawable(R.drawable.cell);
        Drawable d2=getDrawable(R.drawable.cell_miss);

        findViewById(R.id.l1_button_start).setOnTouchListener(StartButton_listener);
        findViewById(R.id.l1_button_exit).setOnTouchListener(ExitButton_listener);
        findViewById(R.id.l1_button_settings).setOnTouchListener(SettingsButton_listener);
    }

    protected void toGameModeScreen() {
        currentLocation = 1;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gamemode);
        applyTheme.all(currentLocation);
        findViewById(R.id.SingleplayerButton).setOnTouchListener(SingleplayerButtonListener);
        findViewById(R.id.MultiplayerButton).setOnTouchListener(MultiplayerButtonListener);
    }

    protected void toPlayernameScreen(boolean singleplayer) {
        currentLocation = 5;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_input_playernames);
        applyTheme.all(currentLocation);
        if (singleplayer) {
            findViewById(R.id.input_playernames_button_accept).setOnTouchListener(OkButton_listener);
        } else {

        }
        findViewById(R.id.input_playernames_button_return).setOnTouchListener(BackButton_listener);
    }

    protected void toSettings() {
        currentLocation= 4;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);
        applyTheme.all(currentLocation);
    }

    protected void toShipsInput(String playername1, String playername2) {
        currentLocation=6;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_input_ships);
        applyTheme.all(currentLocation);
        int stp = getDisplayWidth()/10;
        setViewHeight(R.id.ll1, stp);
        setViewHeight(R.id.ll2, stp);
        setViewHeight(R.id.ll3, stp);
        setViewHeight(R.id.ll4, stp);
        setViewHeight(R.id.ll5, stp);
        setViewHeight(R.id.ll6, stp);
        setViewHeight(R.id.ll7, stp);
        setViewHeight(R.id.ll8, stp);
        setViewHeight(R.id.ll9, stp);
        setViewHeight(R.id.ll10, stp);

        shipsInput = new ShipsInput();
        player_1 = new Player(playername1);
        player_2 = new Player(playername2);
        shipsInput.initTable();
        ((TextView)findViewById(R.id.textView6)).setText(player_1.name + ", place your ships");
    }

    protected void toGame() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameHandler=new GameHandler();
    }
};