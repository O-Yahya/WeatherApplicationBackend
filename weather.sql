PGDMP                         {           weather    15.4    15.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24678    weather    DATABASE     �   CREATE DATABASE weather WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE weather;
                postgres    false            �            1259    32931    notes    TABLE     }   CREATE TABLE public.notes (
    id bigint NOT NULL,
    content character varying(255),
    date date,
    user_id bigint
);
    DROP TABLE public.notes;
       public         heap    postgres    false            �            1259    32930    notes_id_seq    SEQUENCE     u   CREATE SEQUENCE public.notes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.notes_id_seq;
       public          postgres    false    217            	           0    0    notes_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.notes_id_seq OWNED BY public.notes.id;
          public          postgres    false    216            �            1259    32922    users    TABLE     �  CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255),
    mobile_number character varying(255),
    name character varying(255),
    password character varying(255),
    role character varying(255),
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying, 'SUPERUSER'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    32921 	   users_seq    SEQUENCE     s   CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.users_seq;
       public          postgres    false            j           2604    32934    notes id    DEFAULT     d   ALTER TABLE ONLY public.notes ALTER COLUMN id SET DEFAULT nextval('public.notes_id_seq'::regclass);
 7   ALTER TABLE public.notes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216    217                      0    32931    notes 
   TABLE DATA                 public          postgres    false    217   1                  0    32922    users 
   TABLE DATA                 public          postgres    false    215   Q       
           0    0    notes_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.notes_id_seq', 9, true);
          public          postgres    false    216                       0    0 	   users_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.users_seq', 501, true);
          public          postgres    false    214            o           2606    32936    notes notes_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.notes
    ADD CONSTRAINT notes_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.notes DROP CONSTRAINT notes_pkey;
       public            postgres    false    217            m           2606    32929    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            p           2606    32937     notes fkechaouoa6kus6k1dpix1u91c    FK CONSTRAINT        ALTER TABLE ONLY public.notes
    ADD CONSTRAINT fkechaouoa6kus6k1dpix1u91c FOREIGN KEY (user_id) REFERENCES public.users(id);
 J   ALTER TABLE ONLY public.notes DROP CONSTRAINT fkechaouoa6kus6k1dpix1u91c;
       public          postgres    false    217    215    3181                 x���QK�0��������t��Dd���J�\����$��7-��^ZC�~��ɷ�湄|[>�W��T�%i��JDPirH.�F�X4oJ,����eS��*��Ԃ�ah��I4�,д ��?�Y���%c����m��2����p/9U�$�C�+�/�3�B�u�8E5�.��[Mí��UK0�~t�lC����Z���p��AQ��>�lP�xe�)���%$1p�i���i��˨�eX��*�$��o��;�?F�Y7Bِ@���g� �жC          �  x���Ɏ�J�}?��:�,\��M�����&b(��@�b��_�&����w�9ԧSu4�V,��tgCd؏/��
��%0�.�HR��7��������yEQ��M�4�_���r��� zD|$^��8�v�*�A�|�/h��h�����L��N{�x?;�2�"����N�(�&�Mk��(@Ay(Sfl��2�w[���޴���:���'	�E����[J	(��>���	��!Y�������p��qDk�n"�\s�0;K=���iޗ��5�5�-�E��Q�9N�6� �~4����~#l&���4LC�1��;,k٬�Uj�U�p��U���7}1�P�����yE�'D�fh�[���@$�N׫I��v0���U��ǽV�R3��S�	p�S=[��v��k�ߙv���?chFl�=��#�n��G��ؓ7.�����;;�Jc���pw���HD�����ӷ&I#/I���@,��'n�34�q�Ed���@RM)�W�*���7QXR{iyg�
NM����y:��׶�ᘶ~�xOS��0��٦ ��JF�IW�4�`��N�dm���Y��~�\�(T�	�]h�^�fY|�cK��2@�V+n�4}�?\Ğ�X,��*�L�<��v��y͛{�5�/���ϛk����v��� ��i�5�	�a�7�)�IJ��C��^哛�Q������Sn!oo�� 2�     