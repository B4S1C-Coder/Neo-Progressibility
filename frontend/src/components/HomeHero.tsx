import UiImage from "@/assets/an_ui2.png";
import TagLine from "@/components/TagLine";

export default function HomeHero() {
    return (
        <div className="flex flex-col md:flex-row items-center justify-center h-auto md:h-screen pt-20 md:pt-0">
            <TagLine />
            <img src={UiImage} className="md:w-2/5 md:h-h-full w-4/5 p-0 rounded-2xl shadow-2xl shadow-red-600 hover:shadow-2xl hover:shadow-red-700 transition ease-in-out duration-300"></img>
        </div>
    );
}